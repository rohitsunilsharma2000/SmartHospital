package com.hms.service;


import com.hms.dto.ElectronicMedicalRecordRequest;
import com.hms.dto.ElectronicMedicalRecordResponse;
import com.hms.modal.Appointment;
import com.hms.modal.ElectronicMedicalRecord;
import com.hms.repository.AppointmentRepository;
import com.hms.repository.ElectronicMedicalRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElectronicMedicalRecordService {

    private final ElectronicMedicalRecordRepository emrRepository;
    private final AppointmentRepository appointmentRepository;

    // ✅ Create an EMR
    @Transactional
    public ElectronicMedicalRecordResponse createEMR(ElectronicMedicalRecordRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                                                       .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + request.getAppointmentId()));

        ElectronicMedicalRecord emr = ElectronicMedicalRecord.builder()
                                                             .appointment(appointment)
                                                             .chiefComplaints(request.getChiefComplaints())
                                                             .vitals(request.getVitals())
                                                             .allergies(request.getAllergies())
                                                             .medicalHistory(request.getMedicalHistory())
                                                             .personalHistory(request.getPersonalHistory())
                                                             .generalExamination(request.getGeneralExamination())
                                                             .clinicalExamination(request.getClinicalExamination())
                                                             .systemicExamination(request.getSystemicExamination())
                                                             .diabeticFootExamination(request.getDiabeticFootExamination())
                                                             .fundusExamination(request.getFundusExamination())
                                                             .diagnoses(request.getDiagnoses())
                                                             .build();

        emrRepository.save(emr);
        return ElectronicMedicalRecordResponse.fromEntity(emr);
    }

    // ✅ Retrieve EMR by Appointment ID
    public ElectronicMedicalRecordResponse getEMRByAppointmentId(Long appointmentId) {
        ElectronicMedicalRecord emr = emrRepository.findByAppointmentId(appointmentId)
                                                   .orElseThrow(() -> new RuntimeException("EMR not found for appointment ID: " + appointmentId));
        return ElectronicMedicalRecordResponse.fromEntity(emr);
    }

    // ✅ Update an existing EMR
    @Transactional
    public ElectronicMedicalRecordResponse updateEMR(Long id, ElectronicMedicalRecordRequest request) {
        ElectronicMedicalRecord emr = emrRepository.findById(id)
                                                   .orElseThrow(() -> new RuntimeException("EMR not found with ID: " + id));

        emr.setChiefComplaints(request.getChiefComplaints());
        emr.setVitals(request.getVitals());
        emr.setAllergies(request.getAllergies());
        emr.setMedicalHistory(request.getMedicalHistory());
        emr.setPersonalHistory(request.getPersonalHistory());
        emr.setGeneralExamination(request.getGeneralExamination());
        emr.setClinicalExamination(request.getClinicalExamination());
        emr.setSystemicExamination(request.getSystemicExamination());
        emr.setDiabeticFootExamination(request.getDiabeticFootExamination());
        emr.setFundusExamination(request.getFundusExamination());
        emr.setDiagnoses(request.getDiagnoses());

        emrRepository.save(emr);
        return ElectronicMedicalRecordResponse.fromEntity(emr);
    }

    // ✅ Delete an EMR
    public void deleteEMR(Long id) {
        emrRepository.deleteById(id);
    }
}
