package com.hms.dto;


import lombok.Data;

@Data
public class DoctorRequest {
    private String name;
    private String specialty;
    private String docLicence;
}
