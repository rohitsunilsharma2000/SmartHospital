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

    public Patient save ( Patient patient ) {
        return patientRepository.save(patient);

    }
}
