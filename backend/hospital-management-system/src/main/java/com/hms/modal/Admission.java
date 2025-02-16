package com.hms.modal;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "admissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patient being admitted
    @ManyToOne
    @JoinColumn(name = "admitted_patient_id")
    private Patient admittedPatient;

    // Admission type (e.g., "NORMAL", "MLC", etc.)
    private String admissionType;

    // MLC (Medico-Legal Case) details
    private String mlcCategory;         // e.g., Road Accident, Suicide, Homicide
    private String mlcPoliceStation;
    private String mlcRemarks;

    // Name of the admitter or attender
    private String admitterAttenderName;

    // Admission reason / procedure details (e.g., Surgical, Medical, Procedure, Daycare, Observation, Others)
    @Column(length = 1000)
    private String admissionReasonProcedureDetails;

    // The doctor who is admitting the patient
    @ManyToOne
    @JoinColumn(name = "admitting_doctor_id")
    private Doctor admittingDoctor;

    private LocalDate admissionDate;
    private String govIdType;
    private String admitterAttenderContact;
    private String referrer;

    // Date and time when the admission is recorded
    private LocalDateTime admissionTime;
    private String govIdNumber;

    // Flags for daycare and insurance options
    private Boolean daycarePatient;
    private Boolean enableInsurance;

    // Embedded insurance option (reuse your existing InsuranceOption mapping)
    @Embedded
    private InsuranceOption insuranceOption;
}
