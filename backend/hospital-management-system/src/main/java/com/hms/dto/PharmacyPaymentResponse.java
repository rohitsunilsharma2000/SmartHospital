package com.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyPaymentResponse {
    private Long billId;
    private Double totalAmount;
    private Double amountPaid;
    private Double amountDue;
    private Boolean paymentDone;
    private String message;
}
