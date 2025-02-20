package com.hms.dto;

import com.hms.modal.*;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ElectronicMedicalRecordResponse {
    private Long id;
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

    public static ElectronicMedicalRecordResponse fromEntity(ElectronicMedicalRecord emr) {
        return ElectronicMedicalRecordResponse.builder()
                                              .id(emr.getId())
                                              .appointmentId(emr.getAppointment().getId())
                                              .chiefComplaints(emr.getChiefComplaints())
                                              .vitals(emr.getVitals())
                                              .allergies(emr.getAllergies())
                                              .medicalHistory(emr.getMedicalHistory())
                                              .personalHistory(emr.getPersonalHistory())
                                              .generalExamination(emr.getGeneralExamination())
                                              .clinicalExamination(emr.getClinicalExamination())
                                              .systemicExamination(emr.getSystemicExamination())
                                              .diabeticFootExamination(emr.getDiabeticFootExamination())
                                              .fundusExamination(emr.getFundusExamination())
                                              .diagnoses(emr.getDiagnoses())
                                              .build();
    }
}
