package com.hms.modal;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vitals {
    private Double height;              // in centimeters
    private Double weight;              // in kilograms
    private Double bmi;
    private Double waistCircumference;
    private Integer bpSystolic;
    private Integer bpDiastolic;
    private Integer pulse;
    private Double hc;
}

