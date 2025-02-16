package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "electronic_medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectronicMedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associate this EMR with an Appointment (one-to-one mapping)
    // Associated Appointment (one-to-one mapping)
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    // List of chief complaints (each stored as an embeddable object)
    @ElementCollection
    @CollectionTable(name = "emr_chief_complaints", joinColumns = @JoinColumn(name = "emr_id"))
    private List<ChiefComplaint> chiefComplaints;

    // Vitals stored as an embeddable
    @Embedded
    private Vitals vitals;

    // Allergies as a list of strings
    @ElementCollection
    @CollectionTable(name = "emr_allergies", joinColumns = @JoinColumn(name = "emr_id"))
    @Column(name = "allergy")
    private List<String> allergies;

    // Medical history information as an embeddable
    @Embedded
    private MedicalHistory medicalHistory;

    // Personal history as an embeddable
    @Embedded
    private PersonalHistory personalHistory;

    // Examination sections (each stored as an embeddable)
    // Embedded GeneralExamination with an attribute override for 'bmi'
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "bmi", column = @Column(name = "general_examination_bmi")),
            @AttributeOverride(name = "height", column = @Column(name = "general_examination_height")),
            @AttributeOverride(name = "weight", column = @Column(name = "general_examination_weight"))
    })
    private GeneralExamination generalExamination;

    @Embedded
    private ClinicalExamination clinicalExamination;

    @Embedded
    private SystemicExamination systemicExamination;

    @Embedded
    private DiabeticFootExamination diabeticFootExamination;

    @Embedded
    private FundusExamination fundusExamination;

    // List of prescribed tests for this EMR
    @OneToMany(mappedBy = "electronicMedicalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescribedTest> prescribedTests;

    // List of diagnoses
    @ElementCollection
    @CollectionTable(name = "emr_diagnoses", joinColumns = @JoinColumn(name = "emr_id"))
    private List<Diagnosis> diagnoses;

    // One-to-one association with Prescription details
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
