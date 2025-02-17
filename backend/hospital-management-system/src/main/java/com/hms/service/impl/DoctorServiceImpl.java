package com.hms.service.impl;

import com.hms.dto.CreateDoctorRequest;
import com.hms.modal.Doctor;
import com.hms.repository.DoctorRepository;
import com.hms.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor registerDoctor(CreateDoctorRequest request) {
        log.info("Registering a new doctor: {}", request.getName());

        // Creating doctor instance
        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .department(request.getDepartment())
                .outPatient(request.getOutPatient())
                .busyTime(request.getBusyTime())
                .notificationSchedules(request.getNotificationSchedules())
                .build();

        // Logging the creation of the doctor object
        log.info("Created doctor: {}", doctor);

        // Saving the doctor to the repository
        Doctor savedDoctor = doctorRepository.save(doctor);

        // Logging after saving
        log.info("Doctor saved successfully with ID: {}", savedDoctor.getId());

        return savedDoctor;
    }

    @Override
    public List<Doctor> registerMultipleDoctors(List<CreateDoctorRequest> requests) {
        log.info("Registering multiple doctors. Number of doctors: {}", requests.size());

        // Creating doctor instances
        List<Doctor> doctors = requests.stream()
                .map(request -> {
                    Doctor doctor = Doctor.builder()
                            .name(request.getName())
                            .department(request.getDepartment())
                            .outPatient(request.getOutPatient())
                            .busyTime(request.getBusyTime())
                            .notificationSchedules(request.getNotificationSchedules())
                            .build();

                    log.debug("Created doctor: {}", doctor);
                    return doctor;
                })
                .toList();

        // Logging before saving
        log.info("Saving {} doctors to the repository.", doctors.size());

        // Saving doctors to the repository
        List<Doctor> savedDoctors = doctorRepository.saveAll(doctors);

        // Logging after saving
        log.info("Successfully saved {} doctors.", savedDoctors.size());

        return savedDoctors;
    }

}
