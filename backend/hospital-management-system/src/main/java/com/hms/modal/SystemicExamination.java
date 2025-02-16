package com.hms.modal;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemicExamination {
    // Neurological System
    private String neurologicalMentalStatus;
    private String neurologicalCranialNerves;
    private String neurologicalMotorFunction;
    private String neurologicalSensoryFunction;
    private String neurologicalReflexes;
    private String neurologicalCerebellarFunction;

    // Musculoskeletal System
    private String musculoskeletalJoints;
    private String musculoskeletalMuscleStrength;
    private String musculoskeletalSpine;

    // Endocrine System
    private String endocrineThyroid;
    private String endocrineAdrenal;
    private String endocrineDiabetesManagement;
}
