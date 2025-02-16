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
public class GeneralExamination {
    // Vital Signs
    private String bloodPressure;      // e.g., "120/80 mmHg"
    private String heartRate;          // e.g., "72 bpm"
    private String respiratoryRate;    // e.g., "16 breaths/min"
    private String temperature;        // e.g., "37°C"
    private String oxygenSaturation;   // e.g., "98% SpO2"

    // Physical Appearance
    private String generalCondition;   // e.g., "Well" or "Ill"
    private String nutritionalStatus;  // e.g., "Normal", "Overweight", "Underweight"
    private String skinCondition;      // e.g., "Healthy", "Dry", "Eczema", etc.

    // Weight and Height (can duplicate vitals if needed)
    private Double weight;             // in kg
    private Double height;             // in cm
    private Double bmi;                // in kg/m²
}
