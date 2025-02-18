package com.hms.repository;

import com.hms.modal.ConsultationFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationFeeRepository extends JpaRepository<ConsultationFee, Long> {
    List<ConsultationFee> findByDoctorId( Long doctorId);
    List<ConsultationFee> findByConsultationTypeContainingIgnoreCase(String keyword);
}
