package com.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PharmacyBillResponse {


    private Long billId;
    private Double totalAmount;
    private Double discountApplied;
    private Double insurancePaid;
    private Double amountDue;
    private Boolean paymentDone;
    private String message;
}
