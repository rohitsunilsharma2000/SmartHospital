package com.hms.api;


import com.hms.modal.MedicalLabTest;
import com.hms.service.MedicalLabTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lab-tests")
@RequiredArgsConstructor
public class MedicalLabTestController {

    private final MedicalLabTestService labTestService;

    // ✅ Create or update a lab test
    @PostMapping
    public ResponseEntity<MedicalLabTest> createOrUpdateLabTest( @RequestBody MedicalLabTest labTest) {
        return ResponseEntity.ok(labTestService.saveLabTest(labTest));
    }

    // ✅ Get all lab tests
    @GetMapping
    public ResponseEntity<List<MedicalLabTest>> getAllLabTests() {
        return ResponseEntity.ok(labTestService.getAllLabTests());
    }

    // ✅ Get lab test by ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicalLabTest> getLabTestById(@PathVariable Long id) {
        return ResponseEntity.ok(labTestService.getLabTestById(id));
    }

    // ✅ Search lab tests by name (Type-ahead)
    @GetMapping("/search")
    public ResponseEntity<List<MedicalLabTest>> searchLabTests(@RequestParam String keyword) {
        return ResponseEntity.ok(labTestService.searchLabTests(keyword));
    }

    // ✅ Delete a lab test
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLabTest(@PathVariable Long id) {
        labTestService.deleteLabTest(id);
        return ResponseEntity.ok("Lab test deleted successfully.");
    }

    // ✅ Upload Lab Tests via CSV
    @PostMapping("/upload")
    public ResponseEntity<String> uploadLabTests(@RequestParam("file") MultipartFile file) {
        labTestService.uploadLabTestsFromCsv(file);
        return ResponseEntity.ok("Lab tests uploaded successfully.");
    }
}
