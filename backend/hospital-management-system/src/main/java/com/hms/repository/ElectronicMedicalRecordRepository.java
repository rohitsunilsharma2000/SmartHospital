package com.hms.repository;


import com.hms.modal.ElectronicMedicalRecord;
import com.hms.modal.SlotBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ElectronicMedicalRecordRepository extends JpaRepository<ElectronicMedicalRecord, Long> {

    // ✅ Check if EMR exists for a given SlotBooking ID
    boolean existsBySlotBookingId ( Long bookingId );

    // ✅ Find EMR by Booking ID
    Optional<ElectronicMedicalRecord> findBySlotBookingId ( Long bookingId );

    Optional<ElectronicMedicalRecord> findBySlotBooking ( SlotBooking slotBooking );

}
