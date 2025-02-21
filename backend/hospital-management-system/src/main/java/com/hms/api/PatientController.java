package com.hms.api;

import com.hms.dto.PatientRequest;
import com.hms.dto.PatientResponse;
import com.hms.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    // ✅ Search patients by ID, name, or composite key (type-ahead)
    @GetMapping("/search")
    public ResponseEntity<List<PatientResponse>> searchPatients(
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String hospId,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(patientService.searchPatients(mobile, hospId, name));
    }

    // ✅ Update patient
    @PutMapping
    public ResponseEntity<PatientResponse> updatePatient(@RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.updatePatient(request));
    }

    // ✅ Delete patient by composite key
    @DeleteMapping("/{mobile}/{hospId}")
    public ResponseEntity<String> deletePatient(@PathVariable String mobile, @PathVariable String hospId) {
        patientService.deletePatient(mobile, hospId);
        return ResponseEntity.ok("Patient deleted successfully.");
    }
}
