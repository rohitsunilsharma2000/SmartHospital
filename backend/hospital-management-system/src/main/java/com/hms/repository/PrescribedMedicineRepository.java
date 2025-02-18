package com.hms.repository;

import com.hms.modal.PrescribedMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescribedMedicineRepository extends JpaRepository<PrescribedMedicine, Long> {
}
