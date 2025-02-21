package com.hms.repository;

import com.hms.modal.Patient;
import com.hms.modal.PatientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, PatientId> {

    // ✅ Check if patient exists by composite key (mobile + hospId)
    boolean existsById(PatientId id);

    // ✅ Search by composite key (mobile + hospId)
    List<Patient> findByIdMobileContainingIgnoreCaseOrIdHospIdContainingIgnoreCase( String mobile, String hospId);

    // ✅ Search by name (first name or last name) - Type-ahead
    List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    // ✅ Search by exact ID (mobile + hospId)
    @Query("SELECT p FROM Patient p WHERE p.id.mobile = :mobile AND p.id.hospId = :hospId")
    Patient findByCompositeKey( @Param("mobile") String mobile, @Param("hospId") String hospId);

    // ✅ Search by composite key using built-in JPA method
    Optional<Patient> findByIdMobileAndIdHospId( String mobile, String hospId);




}
