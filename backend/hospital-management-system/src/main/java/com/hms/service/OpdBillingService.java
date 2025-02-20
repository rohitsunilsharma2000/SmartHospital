package com.hms.service;


import com.hms.dto.BillingItemRequest;
import com.hms.dto.OpdBillRequest;
import com.hms.dto.OpdBillResponse;
import com.hms.exception.OpdBillAlreadyExistsException;
import com.hms.modal.*;
import com.hms.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class OpdBillingService {



    private final OpdBillRepository opdBillRepository;
    private final AppointmentRepository appointmentRepository;
    private final ElectronicMedicalRecordRepository electronicMedicalRecordRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescribedMedicineRepository prescribedMedicineRepository;
    private final PrescribedTestRepository prescribedTestRepository;
    private final BillingItemRepository billingItemRepository;

    public OpdBillingService ( OpdBillRepository opdBillRepository , AppointmentRepository appointmentRepository , ElectronicMedicalRecordRepository electronicMedicalRecordRepository , PrescriptionRepository prescriptionRepository , PrescribedMedicineRepository prescribedMedicineRepository , PrescribedTestRepository prescribedTestRepository , BillingItemRepository billingItemRepository ) {
        this.opdBillRepository = opdBillRepository;
        this.appointmentRepository = appointmentRepository;
        this.electronicMedicalRecordRepository = electronicMedicalRecordRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.prescribedMedicineRepository = prescribedMedicineRepository;
        this.prescribedTestRepository = prescribedTestRepository;
        this.billingItemRepository = billingItemRepository;
    }



    @Transactional
    public OpdBillResponse createOpdBill(OpdBillRequest request) {
        log.info("🟢 Starting OPD Bill creation for appointment ID: {}", request.getAppointmentId());

        // ✅ Check if OPD Bill already exists for this appointment
        if (opdBillRepository.existsByAppointmentId(request.getAppointmentId())) {
            throw new OpdBillAlreadyExistsException("OPD Bill already exists for appointment ID: " + request.getAppointmentId());
        }

        // ✅ Fetch appointment
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                                                       .orElseThrow(() -> new RuntimeException("❌ Appointment not found for ID: " + request.getAppointmentId()));

        log.info("✅ Appointment found with ID: {}", appointment.getId());

        // ✅ Fetch or create an EMR
        final ElectronicMedicalRecord emr = electronicMedicalRecordRepository.findByAppointmentId(appointment.getId())
                                                                             .orElseGet(() -> electronicMedicalRecordRepository.save(ElectronicMedicalRecord.builder()
                                                                                                                                                            .appointment(appointment)
                                                                                                                                                            .build()));

        // ✅ Fetch or create a Prescription
        final Prescription prescription = Optional.ofNullable(emr.getPrescription())
                                                  .orElseGet(() -> prescriptionRepository.save(Prescription.builder().build()));

        emr.setPrescription(prescription);
        electronicMedicalRecordRepository.save(emr);
        log.info("✅ EMR and Prescription saved successfully.");

        // ✅ Calculate totals
        double billingItemsTotal = Optional.ofNullable(request.getBillingItems())
                                           .map(items -> items.stream().mapToDouble(BillingItemRequest::getTotal).sum())
                                           .orElse(0.0);

        double prescribedMedicinesTotal = Optional.ofNullable(request.getPrescribedMedicines())
                                                  .map(meds -> meds.stream().mapToDouble(med -> med.getUnitPrice() * med.getQuantity()).sum())
                                                  .orElse(0.0);

        double prescribedTestsTotal = Optional.ofNullable(request.getPrescribedTests())
                                              .map(tests -> tests.stream().mapToDouble(test -> test.getUnitPrice() * test.getQuantity()).sum())
                                              .orElse(0.0);

        double totalAmount = billingItemsTotal + prescribedMedicinesTotal + prescribedTestsTotal;
        double discountAmount = Optional.ofNullable(request.getDiscountAmount()).orElse(0.0);
        double finalAmount = totalAmount - discountAmount;

        log.info("💵 Calculated totals: Billing = ₹{}, Medicines = ₹{}, Tests = ₹{}, Discount = ₹{}, Final = ₹{}",
                 billingItemsTotal, prescribedMedicinesTotal, prescribedTestsTotal, discountAmount, finalAmount);

        // ✅ Create OPD Bill
        final OpdBill opdBill = opdBillRepository.save(OpdBill.builder()
                                                              .appointment(appointment)
                                                              .isInsuranceApplied(request.getIsInsuranceApplied())
                                                              .insuranceOption(request.getInsuranceOption())
                                                              .totalAmount(totalAmount)
                                                              .discountAmount(discountAmount)
                                                              .finalAmount(finalAmount)
                                                              .payment(request.getPayment())
                                                              .build());
        log.info("✅ OPD Bill created with ID: {}", opdBill.getId());

        // ✅ Save Billing Items
        List<BillingItem> billingItems = Optional.ofNullable(request.getBillingItems())
                                                 .map(items -> items.stream()
                                                                    .map(item -> BillingItem.builder()
                                                                                            .opdBill(opdBill)
                                                                                            .type(item.getType())
                                                                                            .name(item.getName())
                                                                                            .unitPrice(item.getUnitPrice())
                                                                                            .quantity(item.getQuantity())
                                                                                            .discount(item.getDiscount())
                                                                                            .total(item.getTotal())
                                                                                            .build())
                                                                    .collect(Collectors.toList()))
                                                 .orElseGet(ArrayList::new);

        billingItemRepository.saveAll(billingItems);
        log.info("✅ Saved {} billing items", billingItems.size());

        // ✅ Save Prescribed Medicines
        List<PrescribedMedicine> prescribedMedicines = Optional.ofNullable(request.getPrescribedMedicines())
                                                               .map(meds -> meds.stream()
                                                                                .map(med -> PrescribedMedicine.builder()
                                                                                                              .prescription(prescription)
                                                                                                              .medicineType(med.getMedicineType())
                                                                                                              .medicineBrand(med.getMedicineBrand())
                                                                                                              .dosageTime(med.getDosageTime())
                                                                                                              .numberOfDays(med.getNumberOfDays())
                                                                                                              .unitPrice(med.getUnitPrice())
                                                                                                              .quantity(med.getQuantity())
                                                                                                              .total(med.getUnitPrice() * med.getQuantity())
                                                                                                              .build())
                                                                                .collect(Collectors.toList()))
                                                               .orElseGet(ArrayList::new);

        prescribedMedicineRepository.saveAll(prescribedMedicines);
        log.info("✅ Saved {} prescribed medicines", prescribedMedicines.size());

        // ✅ Save Prescribed Tests
        List<PrescribedTest> prescribedTests = Optional.ofNullable(request.getPrescribedTests())
                                                       .map(tests -> tests.stream()
                                                                          .map(test -> PrescribedTest.builder()
                                                                                                     .electronicMedicalRecord(emr)
                                                                                                     .testName(test.getTestName())
                                                                                                     .quantity(test.getQuantity())
                                                                                                     .unitPrice(test.getUnitPrice())
                                                                                                     .total(test.getUnitPrice() * test.getQuantity())
                                                                                                     .build())
                                                                          .collect(Collectors.toList()))
                                                       .orElseGet(ArrayList::new);

        prescribedTestRepository.saveAll(prescribedTests);
        log.info("✅ Saved {} prescribed tests", prescribedTests.size());

        // ✅ Update OPD Bill and return response
        opdBill.setTotalAmount(totalAmount);
        opdBill.setFinalAmount(finalAmount);
        opdBillRepository.save(opdBill);

        log.info("✅ Final OPD Bill updated: Total = ₹{}, Final = ₹{}", opdBill.getTotalAmount(), opdBill.getFinalAmount());
        log.info("✅ Returning OPD Bill response.");

        return OpdBillResponse.fromEntity2(opdBill, billingItems, prescribedMedicines, prescribedTests);
    }

    public OpdBillResponse getOpdBillByAppointmentId(Long appointmentId) {
        log.info("🟢 Fetching OPD Bill for appointment ID: {}", appointmentId);

        OpdBill opdBill = opdBillRepository.findByAppointmentId(appointmentId)
                                           .orElseThrow(() -> {
                                               log.error("❌ OPD Bill not found for appointment ID: {}", appointmentId);
                                               return new RuntimeException("OPD Bill not found for appointment ID: " + appointmentId);
                                           });

        log.info("✅ OPD Bill found with ID: {}", opdBill.getId());
        return OpdBillResponse.fromEntity(opdBill);
    }

    @Transactional
    public OpdBillResponse updateOpdBill(Long appointmentId, OpdBillRequest request) {
        log.info("🟢 Updating OPD Bill for appointment ID: {}", appointmentId);

        OpdBill opdBill = opdBillRepository.findByAppointmentId(appointmentId)
                                           .orElseThrow(() -> {
                                               log.error("❌ OPD Bill not found for appointment ID: {}", appointmentId);
                                               return new RuntimeException("OPD Bill not found for appointment ID: " + appointmentId);
                                           });

        log.debug("💰 Recalculating total amounts.");
        double billingItemsTotal = request.getBillingItems() != null
                ? request.getBillingItems().stream().mapToDouble(BillingItemRequest::getTotal).sum()
                : 0.0;

        double prescribedMedicinesTotal = request.getPrescribedMedicines() != null
                ? request.getPrescribedMedicines().stream().mapToDouble(med -> med.getUnitPrice() * med.getQuantity()).sum()
                : 0.0;

        double prescribedTestsTotal = request.getPrescribedTests() != null
                ? request.getPrescribedTests().stream().mapToDouble(test -> test.getUnitPrice() * test.getQuantity()).sum()
                : 0.0;

        double totalAmount = billingItemsTotal + prescribedMedicinesTotal + prescribedTestsTotal;
        double discountAmount = request.getDiscountAmount() != null ? request.getDiscountAmount() : 0.0;
        double finalAmount = totalAmount - discountAmount;

        log.info("💸 Updated amounts - Billing Total: ₹{}, Medicines Total: ₹{}, Tests Total: ₹{}", billingItemsTotal, prescribedMedicinesTotal, prescribedTestsTotal);
        log.info("💸 Updated amounts - Discount: ₹{}, Final Amount: ₹{}", discountAmount, finalAmount);

        opdBill.setTotalAmount(totalAmount);
        opdBill.setDiscountAmount(discountAmount);
        opdBill.setFinalAmount(finalAmount);
        opdBillRepository.save(opdBill);

        log.info("✅ OPD Bill updated successfully with ID: {}", opdBill.getId());

        return OpdBillResponse.fromEntity(opdBill);
    }
}
