package com.hms.api;

import com.hms.dto.DoctorFeeRequest;
import com.hms.dto.DoctorFeeResponse;
import com.hms.service.DoctorFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
public class DoctorFeeController {

    private final DoctorFeeService doctorFeeService;

    // ✅ R: Read fees by doctor name, ID, or consultationType
    @GetMapping
    public ResponseEntity<List<DoctorFeeResponse>> getFees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String consultationType) {
        return ResponseEntity.ok(doctorFeeService.getFees(name, id, consultationType));
    }

    // ✅ U: Update fee
    @PutMapping("/{id}")
    public ResponseEntity<DoctorFeeResponse> updateFee(
            @PathVariable Long id, @RequestBody DoctorFeeRequest request) {
        return ResponseEntity.ok(doctorFeeService.updateFee(id, request));
    }

    // ✅ D: Delete fee
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFee(@PathVariable Long id) {
        doctorFeeService.deleteFee(id);
        return ResponseEntity.ok("Fee deleted successfully.");
    }
}

