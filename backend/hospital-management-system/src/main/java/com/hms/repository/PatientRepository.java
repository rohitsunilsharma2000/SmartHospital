package com.hms.repository;


import com.hms.modal.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // ✅ Search By ID - Type-Ahead (Long Type)
    @Query("SELECT p FROM Patient p WHERE CAST(p.id AS string) LIKE CONCAT(:query, '%')")
    List<Patient> searchById(@Param("query") String query);

    // ✅ Search By Name - Type-Ahead (Ignore Case)
    List<Patient> findByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(String firstName, String lastName);
}
