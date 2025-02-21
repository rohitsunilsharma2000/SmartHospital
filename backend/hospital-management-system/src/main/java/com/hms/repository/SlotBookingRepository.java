package com.hms.repository;

import com.hms.modal.SlotBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotBookingRepository extends JpaRepository<SlotBooking, Long> {

    boolean existsBySlotId(Long slotId);

    // Type-ahead search
    List<SlotBooking> findByPatientIdMobileContainingIgnoreCaseOrPatientIdHospIdContainingIgnoreCaseOrPatientFileNumberContainingIgnoreCaseOrPatientFatherNameContainingIgnoreCaseOrPatientMotherNameContainingIgnoreCaseOrPatientDateOfBirthContainingIgnoreCase(
            String mobile, String hospId, String fileNumber, String fatherName, String motherName, String dateOfBirth);
}
