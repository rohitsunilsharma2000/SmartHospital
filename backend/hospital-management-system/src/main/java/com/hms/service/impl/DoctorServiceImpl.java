package com.hms.service.impl;

import com.hms.dto.CreateDoctorRequest;
import com.hms.modal.Availability;
import com.hms.modal.Doctor;
import com.hms.repository.AvailabilityRepository;
import com.hms.repository.DoctorRepository;
import com.hms.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AvailabilityRepository availabilityRepository;
    public DoctorServiceImpl( DoctorRepository doctorRepository , AvailabilityRepository availabilityRepository ) {
        this.doctorRepository = doctorRepository;
        this.availabilityRepository = availabilityRepository;
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
        List<Availability> availabilities = request.getAvailability().stream()
                                                   .map(a -> Availability.builder()
                                                                         .day(a.getDay().toUpperCase()) // Ensure the correct field name is used
                                                                         .startTime(a.getStartTime())
                                                                         .endTime(a.getEndTime())
                                                                         .doctor(doctor)
                                                                         .build())
                                                   .collect(Collectors.toList());

        availabilityRepository.saveAll(availabilities);

        // Logging after saving
        log.info("Doctor saved successfully with ID: {}", savedDoctor.getId());

        return savedDoctor;
    }

    @Override
    @Transactional
    public List<Doctor> registerMultipleDoctors(List<CreateDoctorRequest> requests) {
        log.info("Registering multiple doctors. Number of doctors: {}", requests.size());

        // Creating doctor instances
        List<Doctor> doctors = requests.stream()
                                       .map(request -> Doctor.builder()
                                                             .name(request.getName())
                                                             .department(request.getDepartment())
                                                             .outPatient(request.getOutPatient())
                                                             .busyTime(request.getBusyTime())
                                                             .notificationSchedules(request.getNotificationSchedules())
                                                             .build())
                                       .collect(Collectors.toList());

        log.info("Saving {} doctors to the repository.", doctors.size());

        // Saving doctors in batch
        List<Doctor> savedDoctors = doctorRepository.saveAll(doctors);
        log.info("Successfully saved {} doctors.", savedDoctors.size());

        // Adding availability for each doctor
        List<Availability> availabilities = savedDoctors.stream()
                                                        .flatMap(doctor -> {
                                                            CreateDoctorRequest request = requests.get(savedDoctors.indexOf(doctor)); // Get corresponding request
                                                            return request.getAvailability().stream()
                                                                          .map(a -> Availability.builder()
                                                                                                .day(a.getDay().toUpperCase())
                                                                                                .startTime(a.getStartTime())
                                                                                                .endTime(a.getEndTime())
                                                                                                .doctor(doctor)
                                                                                                .build());
                                                        })
                                                        .collect(Collectors.toList());

        log.info("Saving {} availability entries.", availabilities.size());
        availabilityRepository.saveAll(availabilities);
        log.info("Successfully saved all availability records.");

        return savedDoctors;
    }
}
