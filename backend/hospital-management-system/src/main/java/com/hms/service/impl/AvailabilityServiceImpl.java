package com.hms.service.impl;

import com.hms.dto.CreateAvailabilityRequest;
import com.hms.enums.SlotStatus;
import com.hms.modal.Availability;
import com.hms.modal.Doctor;
import com.hms.repository.AvailabilityRepository;
import com.hms.repository.DoctorRepository;
import com.hms.service.AvailabilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, DoctorRepository doctorRepository) {
        this.availabilityRepository = availabilityRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional
    public List<Availability> addAvailability(Long doctorId, CreateAvailabilityRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        LocalTime startTime = LocalTime.parse(request.getStartTime());
        LocalTime endTime = LocalTime.parse(request.getEndTime());

        // Ensure that only full hours are considered
        if (startTime.getMinute() != 0 || endTime.getMinute() != 0) {
            throw new RuntimeException("Start and end time must be at the beginning of an hour (e.g., 10:00 AM, 11:00 AM)");
        }

        List<Availability> slots = new ArrayList<>();

        while (!startTime.equals(endTime)) {
            for (int i = 0; i < 6; i++) {
                LocalTime slotStart = startTime.plusMinutes(i * 10);
                LocalTime slotEnd = slotStart.plusMinutes(10);

                // Check if this slot already exists
                if (availabilityRepository.existsByDoctorIdAndStartTime(doctorId, slotStart.toString())) {
                    throw new RuntimeException("Slot at " + slotStart + " already exists for Doctor ID: " + doctorId);
                }

                Availability availability = Availability.builder()
                                                        .doctor(doctor)
                                                        .startTime(slotStart.toString())
                                                        .endTime(slotEnd.toString())
                                                        .consultationType(request.getConsultationType())
                                                        .status(SlotStatus.AVAILABLE)
                                                        .build();

                slots.add(availability);
            }

            startTime = startTime.plusHours(1); // Move to next hour
        }

        return availabilityRepository.saveAll(slots);
    }

    @Override
    public List<Availability> getAvailabilitiesForDoctor(Long doctorId) {
        return availabilityRepository.findByDoctorId(doctorId);
    }

    @Override
    public Availability updateAvailability ( Long doctorId , Long availabilityId , CreateAvailabilityRequest request ) {
        return null;
    }

    @Override
    public void deleteAvailability ( Long doctorId , Long availabilityId ) {

    }
}
