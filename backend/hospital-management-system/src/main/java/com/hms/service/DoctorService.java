package com.hms.service;

import com.hms.exception.DoctorAlreadyExistsException;
import com.hms.modal.Doctor;
import com.hms.modal.SlotAvailability;
import com.hms.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService ( DoctorRepository doctorRepository ) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor ( Doctor doctor ) {

        if(doctorRepository.findByDocLicence(doctor.getDocLicence()).isPresent()){
            throw new DoctorAlreadyExistsException ("Doctor with licence " + doctor.getDocLicence() + " already exists.");
        }

            // Set the parent doctor on each slot availability
            if (doctor.getSlotAvailabilities() != null) {
                for (SlotAvailability slot : doctor.getSlotAvailabilities()) {
                    slot.setDoctor(doctor);
                }

        }
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctor ( Long id ) {
        return doctorRepository.findById(id)
                               .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

}
