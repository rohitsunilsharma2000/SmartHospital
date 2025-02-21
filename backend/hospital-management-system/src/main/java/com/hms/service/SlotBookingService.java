package com.hms.service;

import com.hms.dto.SlotBookingRequest;
import com.hms.dto.SlotBookingResponse;
import com.hms.enums.SlotStatus;
import com.hms.exception.SlotAlreadyBookedException;
import com.hms.modal.*;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.repository.SlotAvailabilityRepository;
import com.hms.repository.SlotBookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlotBookingService {

    private final SlotBookingRepository slotBookingRepository;
    private final SlotAvailabilityRepository slotAvailabilityRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    // ✅ Book a slot
    @Transactional
    public SlotBookingResponse bookSlot(SlotBookingRequest request) {
        log.info("Booking slot for patient: {} at hospital: {}", request.getMobile(), request.getHospId());

        // ✅ Validate patient
        Patient patient = patientRepository.findById(new PatientId(request.getMobile(), request.getHospId()))
                                           .orElseThrow(() -> new RuntimeException("Patient not found"));

        // ✅ Validate doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // ✅ Validate slot by slotNumber
        SlotAvailability slot = slotAvailabilityRepository.findBySlotNumber(request.getSlotNumber())
                                                          .orElseThrow(() -> new RuntimeException("Slot not found with slotNumber: " + request.getSlotNumber()));

        if (slotBookingRepository.existsBySlotId(slot.getId())) {
            log.error("Slot {} is already booked", slot.getId());
            throw new SlotAlreadyBookedException("Slot is already booked");
        }

        // ✅ Update slot status to BOOKED
        slot.setStatus(SlotStatus.BOOKED);
        slotAvailabilityRepository.save(slot);
        log.info("Slot {} status updated to BOOKED", slot.getId());

        // ✅ Create booking
        SlotBooking booking = new SlotBooking();
        booking.setPatient(patient);
        booking.setDoctor(doctor);
        booking.setSlot(slot);
        booking.setSource(request.getSource());
        booking.setPatientType(request.getPatientType());
        booking.setVisitType(request.getVisitType());
        booking.setReVisitHospId(request.getReVisitHospId());
        booking.setEmailNotifications(request.isEmailNotifications());

        SlotBooking savedBooking = slotBookingRepository.save(booking);
        log.info("Slot booked successfully with ID: {}", savedBooking.getId());

        return mapToResponse(savedBooking);
    }

    // ✅ Get all bookings
    public List<SlotBookingResponse> getAllBookings() {
        return slotBookingRepository.findAll().stream()
                                    .map(this::mapToResponse)
                                    .collect(Collectors.toList());
    }

    // ✅ Get booking by ID
    public SlotBookingResponse getBookingById(Long id) {
        SlotBooking booking = slotBookingRepository.findById(id)
                                                   .orElseThrow(() -> new RuntimeException("Booking not found"));
        return mapToResponse(booking);
    }

    // ✅ Type-ahead search
    public List<SlotBookingResponse> searchBookings(String keyword) {
        return slotBookingRepository.findByPatientIdMobileContainingIgnoreCaseOrPatientIdHospIdContainingIgnoreCaseOrPatientFileNumberContainingIgnoreCaseOrPatientFatherNameContainingIgnoreCaseOrPatientMotherNameContainingIgnoreCaseOrPatientDateOfBirthContainingIgnoreCase(
                                            keyword, keyword, keyword, keyword, keyword, keyword)
                                    .stream()
                                    .map(this::mapToResponse)
                                    .collect(Collectors.toList());
    }

    // ✅ Cancel booking
    public void cancelBooking(Long id) {
        slotBookingRepository.deleteById(id);
        log.info("Booking with ID {} canceled successfully", id);
    }

    // ✅ Map entity to response
    private SlotBookingResponse mapToResponse(SlotBooking booking) {
        SlotBookingResponse response = new SlotBookingResponse();
        response.setBookingId(booking.getId());
        response.setPatientName(booking.getPatient().getFirstName() + " " + booking.getPatient().getLastName());
        response.setDoctorName(booking.getDoctor().getName());
        response.setSlotTime(booking.getSlot().getTime());
        response.setVisitType(booking.getVisitType());
        response.setSource(booking.getSource());
        response.setEmailNotifications(booking.isEmailNotifications());
        return response;
    }
}
