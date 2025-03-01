package com.hms.repository;


import com.hms.modal.InsuranceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<InsuranceOption, Long> {
}
