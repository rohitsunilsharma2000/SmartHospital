package com.hms.repository;

import com.hms.modal.PrescribedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescribedTestRepository extends JpaRepository<PrescribedTest, Long> {
}
