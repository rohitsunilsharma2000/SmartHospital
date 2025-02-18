package com.hms.dto;

import com.hms.modal.BillingItem;
import com.hms.modal.InsuranceOption;
import com.hms.modal.Payment;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OpdBillResponse {
    private Long id;
    private Long appointmentId;
    private Boolean isInsuranceApplied;
    private InsuranceOption insuranceOption;
    private List<BillingItemResponse> billingItems; // ✅ No reference back to OpdBill
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
    private Payment payment; // ✅ Directly included (Embedded)

    public static OpdBillResponse fromEntity(com.hms.modal.OpdBill opdBill) {
        return OpdBillResponse.builder()
                              .id(opdBill.getId())
                              .appointmentId(opdBill.getAppointment().getId())
                              .isInsuranceApplied(opdBill.getIsInsuranceApplied())
                              .insuranceOption(opdBill.getInsuranceOption())
                              .billingItems(opdBill.getBillingItems().stream()
                                                   .map(BillingItemResponse::fromEntity)
                                                   .collect(Collectors.toList()))
                              .totalAmount(opdBill.getTotalAmount())
                              .discountAmount(opdBill.getDiscountAmount())
                              .finalAmount(opdBill.getFinalAmount())
                              .payment(opdBill.getPayment()) // ✅ No need for extra transformation
                              .build();
    }
}
