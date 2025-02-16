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
public class DiabeticFootExamination {
    // Inspection
    private String footInspectionSkinIntegrity; // e.g., "Ulcers, Blisters, Calluses"
    private String footInspectionDeformities;
    private String footInspectionInfection;

    // Palpation
    private String footPalpationTemperature;
    private String footPalpationPulse;
    private String footPalpationSensation;

    // Neuropathy Screening
    private String neuropathyMonofilamentTest;
    private String neuropathyVibrationTest;
    private String neuropathyPinprickTest;

    // Vascular Assessment
    private String vascularAnkleBrachialIndex;  // e.g., "1.0"
    private String vascularCapillaryRefillTime; // in seconds
}
