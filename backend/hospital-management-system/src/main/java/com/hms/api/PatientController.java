package com.hms.api;


import com.hms.modal.Patient;
import com.hms.repository.PatientRepository;
import com.hms.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService ) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.save(patient);
        return ResponseEntity.ok(savedPatient);
    }
    // ✅ 2. Search Patients By ID (Type-Ahead)
    @GetMapping("/searchById")
    public ResponseEntity<List<Patient>> searchPatientsById(@RequestParam String query) {
        List<Patient> patients = patientService.searchPatientsById(query);
        return ResponseEntity.ok(patients);
    }

    // ✅ 3. Search Patients By Name (Type-Ahead)
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatientsByName(@RequestParam String query) {
        List<Patient> patients = patientService.searchPatientsByName(query);
        return ResponseEntity.ok(patients);
    }
}
