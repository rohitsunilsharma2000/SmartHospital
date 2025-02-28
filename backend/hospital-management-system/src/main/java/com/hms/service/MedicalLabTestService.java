package com.hms.service;


import com.hms.modal.MedicalLabTest;
import com.hms.repository.MedicalLabTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalLabTestService {

    private final MedicalLabTestRepository medicalLabTestRepository;

    // ✅ Create or update a lab test
    public MedicalLabTest saveLabTest( MedicalLabTest labTest) {
        return medicalLabTestRepository.save(labTest);
    }

    // ✅ Get all lab tests
    public List<MedicalLabTest> getAllLabTests() {
        return medicalLabTestRepository.findAll();
    }

    // ✅ Get lab test by ID
    public MedicalLabTest getLabTestById(Long id) {
        return medicalLabTestRepository.findById(id)
                                       .orElseThrow(() -> new RuntimeException("Lab test not found"));
    }

    // ✅ Type-ahead search
    public List<MedicalLabTest> searchLabTests(String keyword) {
        return medicalLabTestRepository.findByTestNameContainingIgnoreCase(keyword);
    }

    // ✅ Delete a lab test
    public void deleteLabTest(Long id) {
        medicalLabTestRepository.deleteById(id);
    }

    // ✅ Upload CSV File & Save Multiple Lab Tests
    public void uploadLabTestsFromCsv(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<MedicalLabTest> labTests = reader.lines()
                                                  .skip(1) // Skip CSV Header
                                                  .map(line -> {
                                                      String[] data = line.split(",");
                                                      return new MedicalLabTest(
                                                              null,
                                                              data[0], // testName
                                                              data[1], // category
                                                              data[2], // description
                                                              new BigDecimal(data[3]), // cost
                                                              Boolean.parseBoolean(data[4]) // fastingRequired
                                                      );
                                                  })
                                                  .collect(Collectors.toList());
            medicalLabTestRepository.saveAll(labTests);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload CSV file: " + e.getMessage());
        }
    }
}
