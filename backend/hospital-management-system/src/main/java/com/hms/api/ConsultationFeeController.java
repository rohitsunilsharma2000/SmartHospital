package com.hms.api;

import com.hms.dto.ConsultationFeeRequest;
import com.hms.dto.ConsultationFeeResponse;
import com.hms.service.ConsultationFeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationFeeController {

    private final ConsultationFeeService consultationFeeService;

    public ConsultationFeeController( ConsultationFeeService consultationFeeService) {
        this.consultationFeeService = consultationFeeService;
    }

    // Fetch all consultation fees for a doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ConsultationFeeResponse>> getConsultationFees( @PathVariable Long doctorId) {
        return ResponseEntity.ok(consultationFeeService.getConsultationFeesForDoctor(doctorId));
    }

    // Typeahead search for consultation types
    @GetMapping("/search")
    public ResponseEntity<List<ConsultationFeeResponse>> searchConsultations(@RequestParam String keyword) {
        return ResponseEntity.ok(consultationFeeService.searchConsultations(keyword));
    }

    // Add a consultation fee for a doctor
    @PostMapping("/doctor/{doctorId}")
    public ResponseEntity<ConsultationFeeResponse> addConsultationFee(
            @PathVariable Long doctorId,
            @RequestParam String consultationType,
            @RequestParam Double unitPrice) {
        return ResponseEntity.ok(consultationFeeService.addConsultationFee(doctorId, consultationType, unitPrice));
    }

    // Bulk Add Consultation Fees for a Doctor
    @PostMapping("/doctor/{doctorId}/bulk")
    public ResponseEntity<List<ConsultationFeeResponse>> addBulkConsultationFees(
            @PathVariable Long doctorId,
            @RequestBody List<ConsultationFeeRequest> fees) {
        return ResponseEntity.ok(consultationFeeService.addBulkConsultationFees(doctorId, fees));
    }
}
