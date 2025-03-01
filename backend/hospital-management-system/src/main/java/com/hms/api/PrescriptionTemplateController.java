package com.hms.api;


import com.hms.dto.PrescriptionTemplateRequest;
import com.hms.modal.PrescriptionTemplate;
import com.hms.service.PrescriptionTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class PrescriptionTemplateController {

    private final PrescriptionTemplateService templateService;


    // ✅ Create a new template
    @PostMapping
    public ResponseEntity<PrescriptionTemplate> createTemplate(@RequestBody PrescriptionTemplateRequest request) {
        PrescriptionTemplate savedTemplate = templateService.saveOrUpdateTemplate(null, request);
        return ResponseEntity.ok(savedTemplate);
    }

    // ✅ Update an existing template
    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionTemplate> updateTemplate(@PathVariable Long id,
                                                               @RequestBody PrescriptionTemplateRequest request) {
        PrescriptionTemplate savedTemplate = templateService.saveOrUpdateTemplate(id, request);
        return ResponseEntity.ok(savedTemplate);
    }


    // ✅ Get all templates
    @GetMapping
    public ResponseEntity<List<PrescriptionTemplate>> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }

    // ✅ Get template by ID
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionTemplate> getTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.getTemplateById(id));
    }

    // ✅ Search templates (Type-ahead)
    @GetMapping("/search")
    public ResponseEntity<List<PrescriptionTemplate>> searchTemplates(@RequestParam String keyword) {
        return ResponseEntity.ok(templateService.searchTemplates(keyword));
    }

    // ✅ Delete a template
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.ok("Template deleted successfully.");
    }
}
