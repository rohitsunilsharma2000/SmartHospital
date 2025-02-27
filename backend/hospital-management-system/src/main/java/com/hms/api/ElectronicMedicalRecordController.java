package com.hms.api;



import com.hms.dto.ElectronicMedicalRecordRequest;
import com.hms.modal.ElectronicMedicalRecord;
import com.hms.service.ElectronicMedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emr")
@RequiredArgsConstructor

public class ElectronicMedicalRecordController {

    private final ElectronicMedicalRecordService electronicMedicalRecordService;

    // ✅ Create or Update EMR
    @PostMapping
    public ResponseEntity<String> saveOrUpdateEMR(@RequestBody ElectronicMedicalRecordRequest request) {
        return ResponseEntity.ok(electronicMedicalRecordService.saveOrUpdateEMR(request));
    }



    // ✅ Get EMR by Booking ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ElectronicMedicalRecord> getEMRByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(electronicMedicalRecordService.getEMRByBookingId(bookingId));
    }

    // ✅ Delete EMR
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEMR(@PathVariable Long id) {
        electronicMedicalRecordService.deleteEMR(id);
        return ResponseEntity.ok("EMR deleted successfully.");
    }
}
