package com.hms.repository;


import com.hms.modal.MedicalLabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<MedicalLabTest, Long> {

    // ✅ Search lab tests by name (Type-ahead search)
    List<MedicalLabTest> findByTestNameContainingIgnoreCase(String testName);

    // ✅ Search lab tests by category (Type-ahead search)
    List<MedicalLabTest> findByCategoryContainingIgnoreCase(String category);

    // ✅ Find lab tests with cost greater than a given value
    List<MedicalLabTest> findByCostGreaterThan(Double cost);

    // ✅ Find lab tests with fasting required
    List<MedicalLabTest> findByFastingRequiredTrue();

    // ✅ Find lab tests with fasting NOT required
    List<MedicalLabTest> findByFastingRequiredFalse();
}
