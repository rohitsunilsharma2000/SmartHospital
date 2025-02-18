package com.hms.dto;

import com.hms.modal.BillingItem;
import com.hms.modal.InsuranceOption;
import com.hms.modal.Payment;
import lombok.Data;

import java.util.List;

@Data
public class OpdBillRequest {
    private Long appointmentId;
    private InsuranceOption insuranceOption;
    private List<BillingItem> billingItems;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
    private Boolean isInsuranceApplied;
    private Payment payment; // ✅ Directly embedded in OpdBill
}
