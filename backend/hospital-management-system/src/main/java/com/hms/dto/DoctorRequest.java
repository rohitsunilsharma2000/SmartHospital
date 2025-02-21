package com.hms.dto;


import lombok.Data;

import java.util.List;

@Data
public class DoctorRequest {
    private String name;
    private String specialty;
    private String docLicence;
    private List<DoctorFeeRequest> fees; // ✅ Added fees
}
