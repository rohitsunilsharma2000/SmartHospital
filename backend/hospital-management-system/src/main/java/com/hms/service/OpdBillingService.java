package com.hms.service;


import com.hms.dto.OpdBillRequest;
import com.hms.dto.OpdBillResponse;
import com.hms.modal.*;
import com.hms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpdBillingService {

    private final OpdBillRepository opdBillRepository;
    private final AppointmentRepository appointmentRepository;
    private final BillingItemRepository billingItemRepository;

    public OpdBillingService(OpdBillRepository opdBillRepository,
                             AppointmentRepository appointmentRepository,
                             BillingItemRepository billingItemRepository) {
        this.opdBillRepository = opdBillRepository;
        this.appointmentRepository = appointmentRepository;
        this.billingItemRepository = billingItemRepository;
    }

    @Transactional
    public OpdBillResponse createOpdBill(OpdBillRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                                                       .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Create OPD Bill
        OpdBill opdBill = OpdBill.builder()
                                 .appointment(appointment)
                                 .insuranceOption(request.getInsuranceOption())
                                 .totalAmount(request.getTotalAmount())
                                 .discountAmount(request.getDiscountAmount())
                                 .finalAmount(request.getFinalAmount())
                                 .isInsuranceApplied(request.getIsInsuranceApplied())
                                 .payment(request.getPayment()) // ✅ Embedded Payment
                                 .build();

       final OpdBill finalOpdBill = opdBillRepository.save(opdBill);

        // Save Billing Items
        List<BillingItem> billingItems = request.getBillingItems().stream()
                                                .map(item -> BillingItem.builder()
                                                                        .opdBill(finalOpdBill)
                                                                        .type(item.getType())
                                                                        .name(item.getName())
                                                                        .unitPrice(item.getUnitPrice())
                                                                        .quantity(item.getQuantity())
                                                                        .discount(item.getDiscount())
                                                                        .total(item.getTotal())
                                                                        .build())
                                                .collect(Collectors.toList());

        billingItemRepository.saveAll(billingItems);
        opdBill.setBillingItems(billingItems);

        return OpdBillResponse.fromEntity(opdBill);
    }

    public OpdBillResponse getOpdBill(Long appointmentId) {
        OpdBill opdBill = opdBillRepository.findByAppointmentId(appointmentId)
                                           .orElseThrow(() -> new RuntimeException("OPD Bill not found"));

        return OpdBillResponse.fromEntity(opdBill);
    }
}
