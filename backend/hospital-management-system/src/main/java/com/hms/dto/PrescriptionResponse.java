package com.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {

    private Long id;
    private String doctorName;
    private String patientName;
    private String templateName;
    private List<String> medicines;
    private List<String> customMedicines;
    private List<String> labTests;
    private List<String> customTests;
    private String notes;
}
