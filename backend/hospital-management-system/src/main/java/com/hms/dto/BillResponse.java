package com.hms.dto;

import lombok.Data;

@Data
public class BillResponse {
    private Long id;
    private String patientName;
    private String doctorName;
    private String slotNumber;
    private Double totalAmount;
    private Double amountPaid;
    private String paymentMethod;
    private String transactionId;
    private Boolean paymentDone;

    private Double discountAmount;
    private String discountAuthorizedBy;
    private String insuranceProviderName;
    private Double insurancePaid;
}
