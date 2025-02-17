package com.hms.repository;

import com.hms.modal.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByDoctorId(Long doctorId);

    boolean existsByDoctorIdAndStartTime ( Long doctorId , String toString );


//    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId " +
//            "AND (:targetDate IS NULL OR DATE(a.startTime) = :targetDate) " +
//            "AND (:targetDay IS NULL OR a.day = :targetDay)")
//    List<Availability> findByDoctorIdAndFilter(@Param("doctorId") Long doctorId,
//                                               @Param("targetDate") LocalDate targetDate,
//                                               @Param("targetDay") String targetDay);


//    ✅ Casts startTime to VARCHAR in the query to prevent PostgreSQL errors.
//    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId " +
//            "AND (:targetDate IS NULL OR CAST(a.startTime AS VARCHAR) = CAST(:targetDate AS VARCHAR)) " +
//            "AND (:targetDay IS NULL OR a.day = :targetDay)")
//    List<Availability> findByDoctorIdAndFilter(@Param("doctorId") Long doctorId,
//                                               @Param("targetDate") String targetDate,
//                                               @Param("targetDay") String targetDay);



//    ✅ Ensures compatibility with BYTEA without changing the column type.
//✅ Uses PostgreSQL ::TEXT casting instead of altering the table.
//            Alternative: Use ::TEXT in Native Query
//    If you are using native SQL queries, modify the query:


    @Query(value = "SELECT * FROM availabilities WHERE doctor_id = :doctorId " +
            "AND (:targetDate IS NULL OR start_time::TEXT = :targetDate) " +
            "AND (:targetDay IS NULL OR day = :targetDay)", nativeQuery = true)
    List<Availability> findByDoctorIdAndFilter(@Param("doctorId") Long doctorId,
                                               @Param("targetDate") String targetDate,
                                               @Param("targetDay") String targetDay);


}
