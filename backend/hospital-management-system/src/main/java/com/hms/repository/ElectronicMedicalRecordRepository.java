package com.hms.repository;


import com.hms.modal.ElectronicMedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectronicMedicalRecordRepository extends JpaRepository<ElectronicMedicalRecord, Long> {

    Optional<ElectronicMedicalRecord> findByAppointmentId(Long appointmentId);
}

