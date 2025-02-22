package com.hms.dto;

import lombok.Data;

@Data
public class PdfBillItemDTO {

    private int serialNumber;
    private String particulars;
    private double charges;
    private int quantity;
    private double amount;
}
