package com.hms.dto;

import com.hms.modal.InsuranceOption;
import com.hms.modal.Payment;
import lombok.Data;

import java.util.List;

@Data
public class OpdBillRequest {
    private Long appointmentId;
    private InsuranceOption insuranceOption;
    private List<BillingItemRequest> billingItems;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
    private Boolean isInsuranceApplied;
    private Payment payment; // ✅ Directly embedded in OpdBill

    private List<PrescribedMedicineRequest> prescribedMedicines;
    private List<PrescribedTestRequest> prescribedTests;
}
