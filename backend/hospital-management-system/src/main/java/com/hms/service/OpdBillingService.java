package com.hms.service;


import com.hms.dto.BillingItemRequest;
import com.hms.dto.OpdBillRequest;
import com.hms.dto.OpdBillResponse;
import com.hms.modal.*;
import com.hms.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        log.info("Creating OPD Bill for appointment ID: {}", request.getAppointmentId());

        // Fetch appointment
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                                                       .orElseThrow(() -> new RuntimeException("Appointment not found for ID: " + request.getAppointmentId()));

        log.info("Appointment found: {}", appointment.getId());

        // Fetch or create an EMR
        final ElectronicMedicalRecord emr = electronicMedicalRecordRepository.findByAppointmentId(appointment.getId())
                                                                             .orElseGet(() -> {
                                                                                 log.info("No existing EMR found, creating a new one for appointment ID: {}", appointment.getId());
                                                                                 return electronicMedicalRecordRepository.save(ElectronicMedicalRecord.builder()
                                                                                                                                                      .appointment(appointment)
                                                                                                                                                      .build());
                                                                             });

        // Fetch or create a Prescription
        final Prescription prescription = emr.getPrescription() != null ? emr.getPrescription()
                : prescriptionRepository.save(Prescription.builder().build());

        emr.setPrescription(prescription);
        electronicMedicalRecordRepository.save(emr);

        // Compute Total Amount from Billing Items
        double billingItemsTotal = request.getBillingItems() != null
                ? request.getBillingItems().stream().mapToDouble(BillingItemRequest::getTotal).sum()
                : 0.0;

        // Compute Medicine Costs
        double prescribedMedicinesTotal = request.getPrescribedMedicines() != null
                ? request.getPrescribedMedicines().stream().mapToDouble(med -> med.getUnitPrice() * med.getQuantity()).sum()
                : 0.0;

        // Compute Prescribed Tests Costs
        double prescribedTestsTotal = request.getPrescribedTests() != null
                ? request.getPrescribedTests().stream().mapToDouble(test -> test.getUnitPrice() * test.getQuantity()).sum()
                : 0.0;

        // Compute Final Amount
        double totalAmount = billingItemsTotal + prescribedMedicinesTotal + prescribedTestsTotal;
        double discountAmount = request.getDiscountAmount() != null ? request.getDiscountAmount() : 0.0;
        double finalAmount = totalAmount - discountAmount;

        log.info("Billing Total: ₹{}, Medicines Total: ₹{}, Tests Total: ₹{}", billingItemsTotal, prescribedMedicinesTotal, prescribedTestsTotal);
        log.info("Discount: ₹{}, Final Amount: ₹{}", discountAmount, finalAmount);

        // Create OPD Bill
        final OpdBill opdBill = opdBillRepository.save(OpdBill.builder()
                                                              .appointment(appointment)
                                                              .isInsuranceApplied(request.getIsInsuranceApplied())
                                                              .insuranceOption(request.getInsuranceOption())
                                                              .totalAmount(totalAmount)
                                                              .discountAmount(discountAmount)
                                                              .finalAmount(finalAmount)
                                                              .payment(request.getPayment())
                                                              .build());

        log.info("OPD Bill created with ID: {}", opdBill.getId());

        // Save Billing Items
        List<BillingItem> billingItems = request.getBillingItems() != null
                ? request.getBillingItems().stream()
                         .map(item -> BillingItem.builder()
                                                 .opdBill(opdBill)
                                                 .type(item.getType())
                                                 .name(item.getName())
                                                 .unitPrice(item.getUnitPrice())
                                                 .quantity(item.getQuantity())
                                                 .discount(item.getDiscount())
                                                 .total(item.getTotal())
                                                 .build())
                         .collect(Collectors.toList())
                : new ArrayList<>();

        billingItemRepository.saveAll(billingItems);
        log.info("Saved {} billing items", billingItems.size());

        // Save Prescribed Medicines
        List<PrescribedMedicine> prescribedMedicines = request.getPrescribedMedicines() != null
                ? request.getPrescribedMedicines().stream()
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
                         .collect(Collectors.toList())
                : new ArrayList<>();

        prescribedMedicineRepository.saveAll(prescribedMedicines);
        log.info("Saved {} prescribed medicines", prescribedMedicines.size());

        // Save Prescribed Tests
        List<PrescribedTest> prescribedTests = request.getPrescribedTests() != null
                ? request.getPrescribedTests().stream()
                         .map(test -> PrescribedTest.builder()
                                                    .electronicMedicalRecord(emr)
                                                    .testName(test.getTestName())
                                                    .quantity(test.getQuantity())
                                                    .unitPrice(test.getUnitPrice())
                                                    .total(test.getUnitPrice() * test.getQuantity())
                                                    .build())
                         .collect(Collectors.toList())
                : new ArrayList<>();

        prescribedTestRepository.saveAll(prescribedTests);
        log.info("Saved {} prescribed tests", prescribedTests.size());

        // Ensure the final amount is updated
        opdBill.setTotalAmount(totalAmount);
        opdBill.setFinalAmount(finalAmount);
        opdBillRepository.save(opdBill);

        log.info("Final OPD Bill updated with total: ₹{}, final amount: ₹{}", opdBill.getTotalAmount(), opdBill.getFinalAmount());

        // ✅ Return Response with Billing Items, Medicines, and Tests
        return OpdBillResponse.fromEntity2(opdBill, billingItems, prescribedMedicines, prescribedTests);
    }

    public OpdBillResponse getOpdBill(Long appointmentId) {
        OpdBill opdBill = opdBillRepository.findByAppointmentId(appointmentId)
                                           .orElseThrow(() -> new RuntimeException("OPD Bill not found"));

        return OpdBillResponse.fromEntity(opdBill);
    }
}
