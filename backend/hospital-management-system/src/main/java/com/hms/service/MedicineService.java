package com.hms.service;


import com.hms.dto.MedicineRequest;
import com.hms.dto.MedicineResponse;
import com.hms.exception.MedicineAlreadyExistsException;
import com.hms.exception.MedicineNotFoundException;
import com.hms.modal.Medicine;
import com.hms.repository.MedicineRepository;
import com.hms.util.CSVHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicineService {

    private final MedicineRepository medicineRepository;

    // ✅ Create Medicine
    @Transactional
    public MedicineResponse createMedicine(MedicineRequest request) {
        log.info("Creating new medicine: {}", request.getName());

        if (medicineRepository.existsByNameIgnoreCase(request.getName())) {
            log.error("Medicine already exists: {}", request.getName());
            throw new MedicineAlreadyExistsException("Medicine with name " + request.getName() + " already exists.");
        }

        Medicine medicine = mapToEntity(request);
        Medicine savedMedicine = medicineRepository.save(medicine);
        return mapToResponse(savedMedicine);
    }

    // ✅ Get Medicine by ID
    public MedicineResponse getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                                              .orElseThrow(() -> new MedicineNotFoundException("Medicine not found with ID: " + id));
        return mapToResponse(medicine);
    }

    // ✅ Search Medicines (Type-Ahead)
    public List<MedicineResponse> searchMedicines(String keyword) {
        List<Medicine> medicines = medicineRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                keyword, keyword, keyword);

        return medicines.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ✅ Update Medicine
    @Transactional
    public MedicineResponse updateMedicine(Long id, MedicineRequest request) {
        Medicine medicine = medicineRepository.findById(id)
                                              .orElseThrow(() -> new MedicineNotFoundException("Medicine not found with ID: " + id));

        medicine.setName(request.getName());
        medicine.setBrand(request.getBrand());
        medicine.setDosageForm(request.getDosageForm());
        medicine.setStrength(request.getStrength());
        medicine.setCategory(request.getCategory());
        medicine.setExpiryDate(request.getExpiryDate());
        medicine.setStockQuantity(request.getStockQuantity());
        medicine.setPrice(request.getPrice());
        medicine.setReorderLevel(request.getReorderLevel());

        Medicine updatedMedicine = medicineRepository.save(medicine);
        return mapToResponse(updatedMedicine);
    }

    // ✅ Delete Medicine
    @Transactional
    public void deleteMedicine(Long id) {
        if (!medicineRepository.existsById(id)) {
            throw new MedicineNotFoundException("Medicine not found with ID: " + id);
        }
        medicineRepository.deleteById(id);
    }


    // ✅ Upload CSV and Save Medicines in Bulk
    @Transactional
    public String uploadMedicines( MultipartFile file) {
        if (!CSVHelper.hasCSVFormat(file)) {
            throw new RuntimeException("Invalid file format. Please upload a CSV file.");
        }

        try {
            List<MedicineRequest> medicines = CSVHelper.csvToMedicines(file.getInputStream());

            List<Medicine> savedMedicines = medicines.stream()
                                                     .filter(medicine -> !medicineRepository.existsByNameIgnoreCase(medicine.getName()))
                                                     .map(this::mapToEntity)
                                                     .collect(Collectors.toList());

            medicineRepository.saveAll(savedMedicines);

            return "Uploaded " + savedMedicines.size() + " medicines successfully.";
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload CSV file: " + e.getMessage());
        }
    }

    // ✅ Mapping Methods
    private Medicine mapToEntity(MedicineRequest request) {
        return new Medicine(null, request.getName(), request.getBrand(), request.getDosageForm(), request.getStrength(),
                            request.getCategory(), request.getExpiryDate(), request.getStockQuantity(), request.getPrice(),
                            request.getReorderLevel());
    }

    private MedicineResponse mapToResponse(Medicine medicine) {
        return new MedicineResponse(medicine.getId(), medicine.getName(), medicine.getBrand(), medicine.getDosageForm(),
                                    medicine.getStrength(), medicine.getCategory(), medicine.getExpiryDate(), medicine.getStockQuantity(),
                                    medicine.getPrice(), medicine.getReorderLevel());
    }
}
