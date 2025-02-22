package com.hms.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PdfBillDTO {

    private String clinicName;
    private String clinicAddress;
    private String phoneNumber;
    private String email;
    private String website;
    private String ivrPhone;

    private String patientName;
    private String contactNumber;
    private String patientId;
    private String ageSex;
    private LocalDateTime dateTime;
    private String billNumber;
    private String doctorName;

    private List<PdfBillItemDTO> items;

    private double totalBilled;
    private double discount;
    private double insurancePaid;
    private double finalPayableAmount;
    private double amountPaid;

    private String amountInWords;
    private String receiptNumber;
    private String paymentMode;

    private int tokenNumber;
    private String queue;
    private String room;

    private String authorizedSignatory;
}

