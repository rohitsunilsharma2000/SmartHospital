package com.hms.modal;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundusExamination {
    // Visual Acuity
    private String visualAcuityRightEye; // e.g., "20/20"
    private String visualAcuityLeftEye;  // e.g., "20/40"

    // External Eye Examination
    private String externalEyeLidsAndLashes;
    private String externalEyeConjunctiva;
    private String externalEyeCornea;

    // Fundoscopy
    private String fundoscopyOpticDisc;
    private String fundoscopyRetinalVessels;
    private String fundoscopyMacula;
    private String fundoscopyRetinalFindings;

    // Pupil Reflex
    private String pupilReflex; // e.g., "Normal", "Abnormal"
}
