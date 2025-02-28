package com.hms.repository;


import com.hms.modal.MedicalLabTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalLabTestRepository extends JpaRepository<MedicalLabTest, Long> {
    List<MedicalLabTest> findByTestNameContainingIgnoreCase(String testName);
}
