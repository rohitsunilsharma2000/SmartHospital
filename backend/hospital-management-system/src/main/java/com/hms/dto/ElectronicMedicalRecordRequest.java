package com.hms.dto;


import com.hms.modal.*;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ElectronicMedicalRecordRequest {
    private Long appointmentId;
    private List<ChiefComplaint> chiefComplaints;
    private Vitals vitals;
    private List<String> allergies;
    private MedicalHistory medicalHistory;
    private PersonalHistory personalHistory;
    private GeneralExamination generalExamination;
    private ClinicalExamination clinicalExamination;
    private SystemicExamination systemicExamination;
    private DiabeticFootExamination diabeticFootExamination;
    private FundusExamination fundusExamination;
    private List<Diagnosis> diagnoses;
}
