package com.hms.repository;

import com.hms.enums.SlotStatus;
import com.hms.modal.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByDoctorId(Long doctorId);

    boolean existsByDoctorIdAndStartTime ( Long doctorId , String toString );



    @Query(value = "SELECT * FROM availabilities WHERE doctor_id = :doctorId " +
            "AND (:targetDate IS NULL OR start_time::TEXT = :targetDate) " +
            "AND (:targetDay IS NULL OR day = :targetDay)", nativeQuery = true)
    List<Availability> findByDoctorIdAndFilter(@Param("doctorId") Long doctorId,
                                               @Param("targetDate") String targetDate,
                                               @Param("targetDay") String targetDay);



    @Query("SELECT a FROM Availability a WHERE a.id = :slotId AND a.status = 'AVAILABLE'")
    Optional<Availability> findAvailableSlotById( Long slotId);

    @Modifying
    @Transactional
    @Query("UPDATE Availability a SET a.status = 'BOOKED' WHERE a.id = :slotId")
    void markSlotAsBooked(Long slotId);



    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND a.day = :day AND a.date = :date AND a.startTime = :startTime AND a.endTime = :endTime")
    Optional<Availability> findExistingSlot(Long doctorId, String day, LocalDate date, String startTime, String endTime);

    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND a.date IN :dates")
    List<Availability> findSlotsForDoctorAndDates(Long doctorId, List<LocalDate> dates);

    @Modifying
    @Query("UPDATE Availability a SET a.status = :status WHERE a.id = :slotId")
    void updateSlotStatus(@Param("slotId") Long slotId, @Param("status") SlotStatus status);

}
