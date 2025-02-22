package com.hms.dto;

import com.hms.modal.Discount;
import com.hms.modal.InsuranceOption;
import lombok.Data;

@Data
public class BillRequest {
    private Long bookingId;
    private Double totalAmount;
    private Double amountPaid;
    private String paymentMethod;
    private String transactionId;
    private Boolean paymentDone;

    private Discount discount;
    private InsuranceOption insurance;
}
