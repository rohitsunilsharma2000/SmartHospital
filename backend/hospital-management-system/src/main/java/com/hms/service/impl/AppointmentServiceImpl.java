package com.hms.service.impl;


import com.hms.dto.AppointmentRequest;
import com.hms.dto.AppointmentResponse;
import com.hms.enums.SlotStatus;
import com.hms.modal.*;
import com.hms.repository.*;
import com.hms.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AvailabilityRepository availabilityRepository;

    private final SlotReservationRepository slotReservationRepository;


    public AppointmentServiceImpl ( AppointmentRepository appointmentRepository ,
                                    DoctorRepository doctorRepository ,
                                    PatientRepository patientRepository ,
                                    AvailabilityRepository availabilityRepository
            , SlotReservationRepository slotReservationRepository ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.availabilityRepository = availabilityRepository;
        this.slotReservationRepository = slotReservationRepository;
    }

    @Override
    @Transactional
    public AppointmentResponse bookAppointment( AppointmentRequest request) {
        // Fetch Doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Fetch Patient
        Patient patient = patientRepository.findById(request.getPatientId())
                                           .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Fetch and Validate Slot
        Availability slot = availabilityRepository.findById(request.getSlotId())
                                                  .orElseThrow(() -> new RuntimeException("Slot not available or already booked"));

        if (!slot.getStatus().equals(SlotStatus.AVAILABLE)) {
            throw new RuntimeException("Slot is not available for booking");
        }

        // Book Appointment
        Appointment appointment = Appointment.builder()
                                             .doctor(doctor)
                                             .patient(patient)
                                             .slot(slot)
                                             .appointmentDateTime(LocalDateTime.now())
                                             .appointmentType(request.getAppointmentType())
                                             .status("SCHEDULED")
                                             .build();

        appointment = appointmentRepository.save(appointment);

        // Mark Slot as Booked
        slot.setStatus(SlotStatus.BOOKED);
        availabilityRepository.save(slot);



        return AppointmentResponse.fromEntity(appointment);
    }


}
