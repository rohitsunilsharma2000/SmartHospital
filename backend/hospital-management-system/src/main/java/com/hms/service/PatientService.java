package com.hms.service;

import com.hms.modal.Patient;
import com.hms.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;


    // ✅ Get Patient By ID
    public Optional<Patient> getPatientById ( Long id ) {
        return patientRepository.findById(id);
    }

    // ✅ Search Patients By ID (Type-Ahead)
    public List<Patient> searchPatientsById ( String query ) {
        return patientRepository.searchById(query); // Updated Method
    }

    // ✅ Search Patients By Name (Type-Ahead)
    public List<Patient> searchPatientsByName ( String query ) {
        return patientRepository.findByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(query , query);
    }

    public Patient save(Patient patient) {
        // ✅ Check for Duplicate by Email
        if (patient.getEmail() != null && patientRepository.existsByEmail(patient.getEmail())) {
            throw new IllegalArgumentException("Patient with email " + patient.getEmail() + " already exists");
        }

        // ✅ Check for Duplicate by Mobile
        if (patient.getMobile() != null && patientRepository.existsByMobile(patient.getMobile())) {
            throw new IllegalArgumentException("Patient with mobile number " + patient.getMobile() + " already exists");
        }

        // ✅ Check for Duplicate by Full Name and Date of Birth
        if (patientRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                patient.getFirstName(), patient.getLastName(), patient.getDateOfBirth())) {
            throw new IllegalArgumentException("Patient with the same name and date of birth already exists");
        }

        // ✅ Save Patient if No Duplicate Found
        return patientRepository.save(patient);
    }
}
