package com.hms.dto;


import lombok.Data;

@Data
public class DoctorFeeResponse {
    private Long id;
    private String consultationType;
    private Double unitPrice;
    private Long doctorId;
    private String doctorName;
}
