package com.hms.api;


import com.hms.dto.MedicineRequest;
import com.hms.dto.MedicineResponse;
import com.hms.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
@Validated
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    public ResponseEntity<MedicineResponse> createMedicine(@RequestBody MedicineRequest request) {
        return ResponseEntity.ok(medicineService.createMedicine(request));
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadMedicines(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(medicineService.uploadMedicines(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponse> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MedicineResponse>> searchMedicines(@RequestParam String keyword) {
        return ResponseEntity.ok(medicineService.searchMedicines(keyword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineResponse> updateMedicine(@PathVariable Long id, @RequestBody MedicineRequest request) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok("Medicine deleted successfully.");
    }
}
