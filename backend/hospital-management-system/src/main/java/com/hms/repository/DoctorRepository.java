package com.hms.repository;

import com.hms.modal.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.availabilities")
    List<Doctor> findAllWithAvailabilities ();


    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.availabilities WHERE d.id = :id")
    Optional<Doctor> findByIdWithAvailabilities ( @Param("id") Long id );



    // Type Ahead Search for Doctor's Name
    List<Doctor> findByNameContainingIgnoreCase ( String query );

    // Type-ahead search using partial match on Doctor ID
    @Query("SELECT d FROM Doctor d WHERE CAST(d.id AS string) LIKE CONCAT(:query, '%')")
    List<Doctor> findByIdStartingWith(@Param("query") String query);
}
