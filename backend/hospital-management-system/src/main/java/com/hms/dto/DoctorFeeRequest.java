package com.hms.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorFeeRequest {
    private String consultationType;
    private Double unitPrice;
}
