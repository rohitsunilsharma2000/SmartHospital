package com.hms.repository;

import com.hms.modal.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByDocLicence( String docLicence);

    List<Doctor> findByNameContainingIgnoreCase ( String name );

    boolean existsByDocLicence ( String docLicence );


}
