package com.hms.service.impl;


import com.hms.dto.DoctorSlotResponse;
import com.hms.dto.SlotResponse;
import com.hms.enums.SlotStatus;
import com.hms.modal.Availability;
import com.hms.modal.Doctor;
import com.hms.repository.AvailabilityRepository;
import com.hms.repository.DoctorRepository;
import com.hms.service.DoctorSlotService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorSlotServiceImpl implements DoctorSlotService {

    private final DoctorRepository doctorRepository;
    private final AvailabilityRepository availabilityRepository;

    public DoctorSlotServiceImpl(DoctorRepository doctorRepository, AvailabilityRepository availabilityRepository) {
        this.doctorRepository = doctorRepository;
        this.availabilityRepository = availabilityRepository;
    }

//    ✅ Handles startTime conversion in Java, avoiding database changes.
//    ✅ Prevents PostgreSQL errors without modifying the column type.

    public List<DoctorSlotResponse> getDoctorSlots(Long doctorId, Integer dayOffset, String dayName) {
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        String targetDateString = (dayOffset != null) ? LocalDate.now().plusDays(dayOffset).toString() : null;
        String targetDayOfWeek = (dayName != null) ? dayName.toUpperCase() : null;

        List<SlotResponse> slots = availabilityRepository.findByDoctorIdAndFilter(
                                                                 doctor.getId(), targetDateString, targetDayOfWeek)
                                                         .stream()
                                                         .map(slot -> SlotResponse.builder()
                                                                                  .slotId(slot.getId())
                                                                                  .startTime(slot.getStartTime())
                                                                                  .endTime(slot.getEndTime())
                                                                                  .status(slot.getStatus() != null ? slot.getStatus() : SlotStatus.AVAILABLE) // Fix NULL status
                                                                                  .build())
                                                         .collect(Collectors.toList());

        return List.of(DoctorSlotResponse.builder()
                                         .doctorId(doctor.getId())
                                         .doctorName(doctor.getName())
                                         .department(doctor.getDepartment())
                                         .slots(slots)
                                         .build());
    }

}
