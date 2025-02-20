package com.hms.repository;


import com.hms.modal.Availability;
import com.hms.modal.Doctor;
import com.hms.modal.SlotReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlotReservationRepository extends JpaRepository<SlotReservation, Long> {

    // Find reservation by slot ID
    Optional<SlotReservation> findBySlotId ( Long slotId );

    // Find all reservations for a specific doctor
    List<SlotReservation> findByDoctorId ( Long doctorId );

    // Find all reservations for a specific patient
    List<SlotReservation> findByPatientId ( Long patientId );

    // Delete reservation by slot ID (when appointment is canceled)
    void deleteBySlotId ( Long slotId );


    // Find existing reservation by doctor and slot (for updating patient ID)
    Optional<SlotReservation> findByDoctorIdAndSlotId ( Long doctorId , Long slotId );

    boolean existsByDoctorAndSlot( Doctor doctor, Availability slot);

    boolean existsByDoctorIdAndSlotId ( Long id , Long id1 );
}

