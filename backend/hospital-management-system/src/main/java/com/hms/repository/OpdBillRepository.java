package com.hms.repository;

import com.hms.modal.OpdBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OpdBillRepository extends JpaRepository<OpdBill, Long> {
    Optional<OpdBill> findByAppointmentId( Long appointmentId);

    boolean existsByAppointmentId ( Long appointmentId );
}
