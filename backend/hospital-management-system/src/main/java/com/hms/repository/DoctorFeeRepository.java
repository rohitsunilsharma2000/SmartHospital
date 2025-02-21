package com.hms.repository;

import com.hms.modal.DoctorFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorFeeRepository extends JpaRepository<DoctorFee, Long> {

    // ✅ Find fees by doctor ID
    List<DoctorFee> findByDoctorId ( Long doctorId );

    // ✅ Find fees by doctor name (Typeahead using built-in JPA method)
    List<DoctorFee> findByDoctor_NameContainingIgnoreCase ( String name );

    List<DoctorFee> findByDoctorName ( String name );

    List<DoctorFee> findByConsultationTypeContainingIgnoreCase ( String consultationType );


    // ✅ Find fees by doctor name (Typeahead) Alternate
    // @Query("SELECT f FROM DoctorFee f WHERE LOWER(f.doctor.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    // List<DoctorFee> findByDoctorName(@Param("name") String name);
}
