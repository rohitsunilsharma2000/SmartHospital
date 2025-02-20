package com.hms.service.impl;


import com.hms.dto.AppointmentRequest;
import com.hms.dto.AppointmentResponse;
import com.hms.enums.AppointmentType;
import com.hms.enums.SlotStatus;
import com.hms.modal.Appointment;
import com.hms.modal.Availability;
import com.hms.modal.Doctor;
import com.hms.modal.Patient;
import com.hms.repository.*;
import com.hms.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Slf4j
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
    public AppointmentResponse  bookAppointment ( AppointmentRequest request ) {
        // Fetch Doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Fetch Patient
        Patient patient = patientRepository.findById(request.getPatientId())
                                           .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Fetch and Validate Slot
        Availability slot = availabilityRepository.findById(request.getSlotId())
                                                  .orElseThrow(() -> new RuntimeException(
                                                          "Slot not available or already booked"));

        if (!slot.getStatus().equals(SlotStatus.AVAILABLE)) {
            throw new RuntimeException("Slot is not available for booking");
        }

        // Book Appointment
        Appointment appointment = Appointment.builder()
                                             .doctor(doctor)
                                             .patient(patient)
                                             .slot(slot)
                                             .appointmentType(request.getAppointmentType())
                                             .status("SCHEDULED")

                                             .appointmentType(AppointmentType.IPD)
                                             .mainComplaint("")
                                             .visitType("")
                                             .appointmentDateTime(LocalDateTime.now())
                                             .build();

        appointment = appointmentRepository.save(appointment);

        // Mark Slot as Booked
        slot.setStatus(SlotStatus.BOOKED);
        availabilityRepository.save(slot);


        return AppointmentResponse.fromEntity(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse bookAppointment(AppointmentRequest request, Availability slot) {
        log.info("Booking appointment for Doctor ID: {} and Patient ID: {}", request.getDoctorId(), request.getPatientId());

        // 1. ✅ Fetch Doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // 2. ✅ Fetch Patient
        Patient patient = patientRepository.findById(request.getPatientId())
                                           .orElseThrow(() -> new RuntimeException("Patient not found"));

        // 3. ✅ Validate Slot
        if (!slot.getStatus().equals(SlotStatus.AVAILABLE)) {
            log.warn("Slot with ID {} is not available. Current status: {}", slot.getId(), slot.getStatus());
            throw new RuntimeException("Slot is not available for booking");
        }

        log.info("Slot with ID {} is available for booking", slot.getId());

        // 4. ✅ Book Appointment
        Appointment appointment = Appointment.builder()
                                             .doctor(doctor)
                                             .patient(patient)
                                             .slot(slot)
                                             .appointmentType(request.getAppointmentType())
                                             .status("SCHEDULED")
                                             .mainComplaint(request.getMainComplaint())
                                             .visitType(request.getVisitType())
                                             .appointmentDateTime(request.getAppointmentDateTime())
                                             .build();

        appointment = appointmentRepository.save(appointment);

        // 5. ✅ Mark Slot as Booked
        slot.setStatus(SlotStatus.BOOKED);
        availabilityRepository.save(slot);

        log.info("Appointment booked successfully with ID: {}", appointment.getId());

        return AppointmentResponse.fromEntity(appointment);
    }



}
