package com.hms.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrescriptionTemplateRequest {

    private String templateName;
    private String description;

    private List<Long> medicineIds; // Medicine IDs instead of List<Medicine>
    private List<String> customMedicines;

    private List<Long> labTestIds; // Lab Test IDs instead of List<MedicalLabTest>
    private List<String> customTests;
}
