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
public class Vitals {
    private Double height;              // in centimeters
    private Double weight;              // in kilograms
    private Double bmi;                 // could be automatically calculated
    private Double waistCircumference;  // in centimeters
    private Integer bpSystolic;         // in mmHg
    private Integer bpDiastolic;        // in mmHg
    private Integer pulse;              // beats per minute
    private Double hc;                  // head circumference (if applicable)
}
