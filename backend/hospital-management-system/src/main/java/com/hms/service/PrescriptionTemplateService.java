package com.hms.service;

import com.hms.dto.PrescriptionTemplateRequest;
import com.hms.exception.TemplateNotFoundException;
import com.hms.modal.MedicalLabTest;
import com.hms.modal.Medicine;
import com.hms.modal.PrescriptionTemplate;
import com.hms.repository.MedicineRepository;
import com.hms.repository.MedicalLabTestRepository;
import com.hms.repository.PrescriptionTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionTemplateService {

    private final PrescriptionTemplateRepository templateRepository;
    private final MedicineRepository medicineRepository;
    private final MedicalLabTestRepository labTestRepository;

    // ✅ Create or update a template
//    public PrescriptionTemplate saveTemplate(PrescriptionTemplateRequest request) {
//        PrescriptionTemplate template = new PrescriptionTemplate();
//        template.setTemplateName(request.getTemplateName());
//        template.setDescription(request.getDescription());
//
//        // ✅ Fetch Medicines by IDs
//        if (request.getMedicineIds() != null && !request.getMedicineIds().isEmpty()) {
//            List<Medicine> medicines = medicineRepository.findAllById(request.getMedicineIds());
//            template.setMedicines(medicines);
//        }
//
//        // ✅ Fetch Medical Lab Tests by IDs
//        if (request.getLabTestIds() != null && !request.getLabTestIds().isEmpty()) {
//            List<MedicalLabTest> labTests = labTestRepository.findAllById(request.getLabTestIds());
//            template.setPredefinedMedicalLabTests(labTests);
//        }
//
//        // ✅ Set Custom Medicines and Custom Tests
//        template.setCustomMedicines(request.getCustomMedicines());
//        template.setCustomTests(request.getCustomTests());
//
//        return templateRepository.save(template);
//    }
    @Transactional
    public PrescriptionTemplate saveOrUpdateTemplate(Long templateId, PrescriptionTemplateRequest request) {
        boolean isUpdate = (templateId != null);
        log.info("{} Prescription Template: {}", isUpdate ? "Updating" : "Creating", request.getTemplateName());

        PrescriptionTemplate template;

        // ✅ If updating, fetch existing template
        if (isUpdate) {
            template = templateRepository.findById(templateId)
                                         .orElseThrow(() -> new TemplateNotFoundException("Template not found with ID: " + templateId));
        } else {
            template = new PrescriptionTemplate();
        }

        // ✅ Set basic fields
        template.setTemplateName(request.getTemplateName());
        template.setDescription(request.getDescription());

        // ✅ Fetch and set medicines by IDs
        if (request.getMedicineIds() != null && !request.getMedicineIds().isEmpty()) {
            List<Medicine> medicines = medicineRepository.findAllById(request.getMedicineIds());
            template.setMedicines(medicines);
        } else {
            template.setMedicines(Collections.emptyList()); // Clear medicines if none provided
        }

        // ✅ Fetch and set lab tests by IDs
        if (request.getLabTestIds() != null && !request.getLabTestIds().isEmpty()) {
            List<MedicalLabTest> labTests = labTestRepository.findAllById(request.getLabTestIds());
            template.setPredefinedMedicalLabTests(labTests);
        } else {
            template.setPredefinedMedicalLabTests(Collections.emptyList()); // Clear lab tests if none provided
        }

        // ✅ Set Custom Medicines and Custom Tests (overwrite only if provided)
        template.setCustomMedicines(request.getCustomMedicines() != null ? request.getCustomMedicines() : new ArrayList<>());
        template.setCustomTests(request.getCustomTests() != null ? request.getCustomTests() : new ArrayList<>());

        // ✅ Save and return the updated/new template
        PrescriptionTemplate savedTemplate = templateRepository.save(template);
        log.info("Prescription Template {} successfully with ID: {}", isUpdate ? "updated" : "created", savedTemplate.getId());

        return savedTemplate;
    }

    // ✅ Get all templates
    public List<PrescriptionTemplate> getAllTemplates() {
        return templateRepository.findAll();
    }

    // ✅ Get template by ID
    public PrescriptionTemplate getTemplateById(Long id) {
        return templateRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("Prescription Template not found"));
    }

    // ✅ Search templates (Type-ahead)
    public List<PrescriptionTemplate> searchTemplates(String keyword) {
        return templateRepository.findByTemplateNameContainingIgnoreCase(keyword);
    }

    // ✅ Delete a template
    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }
}

