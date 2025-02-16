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
public class ClinicalExamination {
    // Head and Neck
    private String headAndNeckInspection;
    private String headAndNeckPalpation;
    private String oralCavity;
    private String thyroid;

    // Chest and Lungs
    private String chestInspection;
    private String chestPalpation;
    private String chestPercussion;
    private String chestAuscultation;

    // Cardiovascular System
    private String cardiovascularInspection;
    private String cardiovascularPalpation;
    private String cardiovascularAuscultation;

    // Abdomen
    private String abdomenInspection;
    private String abdomenPalpation;
    private String abdomenPercussion;
    private String abdomenAuscultation;

    // Extremities
    private String extremitiesInspection;
    private String extremitiesPalpation;
    private String extremitiesRangeOfMotion;
}
