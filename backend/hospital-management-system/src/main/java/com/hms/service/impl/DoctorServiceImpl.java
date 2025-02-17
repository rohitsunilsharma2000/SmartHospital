package com.hms.service.impl;

import com.hms.dto.CreateDoctorRequest;
import com.hms.dto.DoctorSlotResponse;
import com.hms.dto.SlotResponse;
import com.hms.enums.SlotStatus;
import com.hms.modal.Availability;
import com.hms.modal.Doctor;
import com.hms.modal.SlotReservation;
import com.hms.repository.AvailabilityRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.SlotReservationRepository;
import com.hms.service.DoctorService;
import com.hms.service.DoctorSlotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorSlotService doctorSlotService;
    private final AvailabilityRepository availabilityRepository;
    private final SlotReservationRepository slotReservationRepository;

    public DoctorServiceImpl ( DoctorRepository doctorRepository , DoctorSlotService doctorSlotService , AvailabilityRepository availabilityRepository , SlotReservationRepository slotReservationRepository ) {
        this.doctorRepository = doctorRepository;
        this.doctorSlotService = doctorSlotService;
        this.availabilityRepository = availabilityRepository;
        this.slotReservationRepository = slotReservationRepository;
    }

    @Override
    @Transactional

    public Doctor registerDoctor ( CreateDoctorRequest request , String startTime , String endTime , List<String> days , List<String> dates ) {
        // Register doctor
        Doctor doctor = Doctor.builder()
                              .name(request.getName())
                              .department(request.getDepartment())
                              .outPatient(request.getOutPatient())
                              .busyTime(request.getBusyTime())
                              .notificationSchedules(request.getNotificationSchedules())
                              .build();

        doctor = doctorRepository.save(doctor);

        // Generate Slots for Doctor
        List<DoctorSlotResponse> slots = doctorSlotService.generateOrUpdateDoctorSlots(doctor.getId() , startTime ,
                                                                                       endTime , days , dates);

        // Track Slot Reservations for the Doctor
        for (DoctorSlotResponse slotResponse : slots) {
            for (SlotResponse slot : slotResponse.getSlots()) {
                Availability slotEntity = availabilityRepository.findById(slot.getSlotId())
                                                                .orElseThrow(
                                                                        () -> new RuntimeException("Slot not found"));

                SlotReservation reservation = SlotReservation.builder()
                                                             .doctor(doctor)
                                                             .slot(slotEntity)
                                                             .status(SlotStatus.AVAILABLE)
                                                             .reservationTime(LocalDateTime.now())
                                                             .notes("Doctor's availability slot created.")
                                                             .build();

                slotReservationRepository.save(reservation);
            }
        }

        return doctor;
    }


    @Override
    @Transactional
    public List<Doctor> registerMultipleDoctors ( List<CreateDoctorRequest> requests , String startTime , String endTime , List<String> days , List<String> dates ) {
        List<Doctor> doctors = requests.stream()
                                       .map(request -> {
                                           Doctor doctor = Doctor.builder()
                                                                 .name(request.getName())
                                                                 .department(request.getDepartment())
                                                                 .outPatient(request.getOutPatient())
                                                                 .busyTime(request.getBusyTime())
                                                                 .notificationSchedules(
                                                                         request.getNotificationSchedules())
                                                                 .build();
                                           return doctorRepository.save(doctor);
                                       })
                                       .toList();

        // Generate Slots & Track Reservations for Each Doctor
        doctors.forEach(doctor -> {
            List<DoctorSlotResponse> slots = doctorSlotService.generateOrUpdateDoctorSlots(doctor.getId() , startTime ,
                                                                                           endTime , days , dates);

            for (DoctorSlotResponse slotResponse : slots) {
                for (SlotResponse slot : slotResponse.getSlots()) {
                    Availability slotEntity = availabilityRepository.findById(slot.getSlotId())
                                                                    .orElseThrow(() -> new RuntimeException(
                                                                            "Slot not found"));

//                     Create Slot Reservation for the Doctor
                    SlotReservation reservation = SlotReservation.builder()
                                                                 .doctor(doctor)
                                                                 .slot(slotEntity)
                                                                 .status(SlotStatus.AVAILABLE)
                                                                 .reservationTime(LocalDateTime.now())
                                                                 .notes("Doctor's availability slot created.")
                                                                 .build();

                    slotReservationRepository.save(reservation);
                }
            }
        });

        return doctors;
    }

}
