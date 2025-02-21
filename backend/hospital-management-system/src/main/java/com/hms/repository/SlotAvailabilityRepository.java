package com.hms.repository;

import com.hms.modal.SlotAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SlotAvailabilityRepository extends JpaRepository<SlotAvailability, Long> {
    List<SlotAvailability> findByDoctorId ( Long doctorId );


    @Query("SELECT COUNT(s) > 0 FROM SlotAvailability s WHERE s.slotNumber = :slotNumber")
    boolean existsBySlotNumberCustom(@Param("slotNumber") String slotNumber);

    Optional<SlotAvailability> findBySlotNumber ( String slotNumber );

    List<SlotAvailability> findByDoctorIdAndStartDateBetween ( Long doctorId , String toString , String toString1 );

    @Modifying
    @Transactional
    @Query("DELETE FROM SlotAvailability s WHERE s.doctor.id = :doctorId AND s.startDate BETWEEN :startDate AND :endDate")
    void deleteByDoctorIdAndDateRange(@Param("doctorId") Long doctorId,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);
}

