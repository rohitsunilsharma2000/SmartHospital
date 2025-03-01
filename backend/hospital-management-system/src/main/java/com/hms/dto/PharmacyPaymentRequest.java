package com.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyPaymentRequest {
    private Long billId;
    private Double amountPaid;
    private String paymentMethod; // "CASH", "CARD", "UPI"
    private String transactionId;
}

