package com.hms.repository;

import com.hms.modal.PatientId;
import com.hms.modal.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatientId( PatientId patientId);
}

