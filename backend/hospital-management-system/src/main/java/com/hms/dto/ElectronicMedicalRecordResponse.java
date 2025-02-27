package com.hms.dto;


import com.hms.modal.ChiefComplaint;
import com.hms.modal.PrescribedTest;
import com.hms.modal.Prescription;
import com.hms.modal.Vitals;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ElectronicMedicalRecordResponse {
    private Long id;
    private Long bookingId;
    private List<ChiefComplaint> chiefComplaints;
    private Vitals vitals;
    private List<String> allergies;
    private List<String> diagnoses;
    private List<PrescribedTest> prescribedTests;
    private Prescription prescription;
}
