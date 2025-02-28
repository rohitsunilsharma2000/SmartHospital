package com.hms.api;

import com.hms.dto.PrescriptionRequest;
import com.hms.dto.PrescriptionResponse;
import com.hms.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    // ✅ Create a new Prescription (with or without template)
    @PostMapping
    public ResponseEntity<PrescriptionResponse> createPrescription( @RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(prescriptionService.createPrescription(request));
    }

    // ✅ Get Prescription by ID
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponse> getPrescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }

    // ✅ Get Prescriptions for a Patient
    @GetMapping("/patient")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByPatient(
            @RequestParam String mobile, @RequestParam String hospId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatient(mobile, hospId));
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionResponse>> getAllPrescription() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }
}
