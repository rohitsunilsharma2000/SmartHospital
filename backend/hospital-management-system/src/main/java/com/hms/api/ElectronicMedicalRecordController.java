package com.hms.api;

import com.hms.dto.ElectronicMedicalRecordRequest;
import com.hms.dto.ElectronicMedicalRecordResponse;
import com.hms.service.ElectronicMedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emr")
@RequiredArgsConstructor
public class ElectronicMedicalRecordController {

    private final ElectronicMedicalRecordService emrService;

    // ✅ Create EMR for a patient
    @PostMapping("/create")
    public ResponseEntity<ElectronicMedicalRecordResponse> createEMR(@RequestBody ElectronicMedicalRecordRequest request) {
        return ResponseEntity.ok(emrService.createEMR(request));
    }

    // ✅ Retrieve EMR by Appointment ID
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<ElectronicMedicalRecordResponse> getEMRByAppointmentId(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(emrService.getEMRByAppointmentId(appointmentId));
    }

    // ✅ Update an existing EMR
    @PutMapping("/update/{id}")
    public ResponseEntity<ElectronicMedicalRecordResponse> updateEMR(@PathVariable Long id, @RequestBody ElectronicMedicalRecordRequest request) {
        return ResponseEntity.ok(emrService.updateEMR(id, request));
    }

    // ✅ Delete an EMR
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEMR(@PathVariable Long id) {
        emrService.deleteEMR(id);
        return ResponseEntity.ok("Electronic Medical Record deleted successfully.");
    }
}
