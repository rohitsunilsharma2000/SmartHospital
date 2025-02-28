package com.hms.service;


import com.hms.dto.PrescriptionRequest;
import com.hms.dto.PrescriptionResponse;
import com.hms.exception.PrescriptionNotFoundException;
import com.hms.modal.*;
import com.hms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PrescriptionTemplateRepository templateRepository;
    private final MedicineRepository medicineRepository;
    private final LabTestRepository labTestRepository;

    @Transactional
    public PrescriptionResponse createPrescription(PrescriptionRequest request) {
        log.info("Creating prescription for patient: {} at hospital: {}", request.getPatientMobile(), request.getPatientHospId());

        // ✅ Validate Patient
        Patient patient = patientRepository.findById(new PatientId(request.getPatientMobile(), request.getPatientHospId()))
                                           .orElseThrow(() -> new RuntimeException("Patient not found"));

        // ✅ Validate Doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setPrescriptionDate(request.getPrescriptionDate());
        prescription.setNotes(request.getNotes());

        if (request.getTemplateId() != null) {
            // ✅ Fetch Prescription Template
            PrescriptionTemplate template = templateRepository.findById(request.getTemplateId())
                                                              .orElseThrow(() -> new RuntimeException("Template not found"));

            // ✅ Clone the collections to avoid shared references
            prescription.setMedicines(new ArrayList<>(template.getMedicines()));  // Creates a new list with copied elements
            prescription.setLabTests(new ArrayList<>(template.getPredefinedMedicalLabTests()));
            prescription.setCustomMedicines(new ArrayList<>(template.getCustomMedicines()));
            prescription.setCustomTests(new ArrayList<>(template.getCustomTests()));

            log.info("Prescription created using template: {}", template.getTemplateName());
        } else {
            // ✅ Manually add medicines & tests (if no template is used)
            if (request.getMedicineIds() != null && !request.getMedicineIds().isEmpty()) {
                List<Medicine> medicines = medicineRepository.findAllById(request.getMedicineIds());
                prescription.setMedicines(new ArrayList<>(medicines)); // Clone list
            }

            if (request.getLabTestIds() != null && !request.getLabTestIds().isEmpty()) {
                List<MedicalLabTest> labTests = labTestRepository.findAllById(request.getLabTestIds());
                prescription.setLabTests(new ArrayList<>(labTests)); // Clone list
            }

            prescription.setCustomMedicines(request.getCustomMedicines());
            prescription.setCustomTests(request.getCustomTests());
        }

        // ✅ Save Prescription
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        log.info("Prescription created successfully with ID: {}", savedPrescription.getId());

        return mapToResponse(savedPrescription);
    }

    private PrescriptionResponse mapToResponse ( Prescription prescription ) {
        return new PrescriptionResponse(
                prescription.getId() ,
                prescription.getDoctor().getName() ,
                prescription.getPatient().getId().getMobile() ,
                prescription.getTemplate() != null?prescription.getTemplate().getTemplateName():"Custom" ,
                prescription.getMedicines().stream().map(Medicine::getName).collect(Collectors.toList()) ,
                prescription.getCustomMedicines() ,
                prescription.getLabTests().stream().map(MedicalLabTest::getTestName).collect(Collectors.toList()) ,
                prescription.getCustomTests() ,
                prescription.getNotes()
        );
    }

    // ✅ Get Prescription by ID
    public PrescriptionResponse getPrescriptionById ( Long id ) {
        log.info("Fetching prescription with ID: {}" , id);
        Prescription prescription = prescriptionRepository.findById(id)
                                                          .orElseThrow(() -> new PrescriptionNotFoundException(
                                                                  "Prescription not found with ID: " + id));
        return mapToResponse(prescription);
    }

    // ✅ Get Prescriptions for a Patient (by Mobile & Hospital ID)
    public List<PrescriptionResponse> getPrescriptionsByPatient ( String mobile , String hospId ) {
        log.info("Fetching prescriptions for patient with Mobile: {} and Hospital ID: {}" , mobile , hospId);
        List<Prescription> prescriptions = prescriptionRepository.findByPatientId(new PatientId(mobile , hospId));

        if (prescriptions.isEmpty()) {
            throw new PrescriptionNotFoundException("No prescriptions found for this patient.");
        }

        return prescriptions.stream()
                            .map(this::mapToResponse)
                            .collect(Collectors.toList());
    }


    public List<PrescriptionResponse> getAllPrescriptions () {
        log.info("Fetching prescriptions for All patient ");
        List<Prescription> prescriptions = prescriptionRepository.findAll();

        if (prescriptions.isEmpty()) {
            throw new PrescriptionNotFoundException("No prescriptions found .");
        }

        return prescriptions.stream()
                            .map(this::mapToResponse)
                            .collect(Collectors.toList());
    }
}
