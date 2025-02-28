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
public class PrescriptionRequest {

    private Long doctorId;
    private String patientMobile;
    private String patientHospId;

    private Long templateId; // Optional - Auto-fill if provided

    private List<Long> medicineIds; // Optional - Predefined medicines
    private List<String> customMedicines; // Optional - Manually added medicines

    private List<Long> labTestIds; // Optional - Predefined lab tests
    private List<String> customTests; // Optional - Manually added lab tests

    private String notes; // Optional doctor's notes

    private String prescriptionDate;

}
