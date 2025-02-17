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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DoctorSlotServiceImpl implements DoctorSlotService {

    private final DoctorRepository doctorRepository;
    private final AvailabilityRepository availabilityRepository;

    // Define valid slot transitions dynamically in a map
    private static final Map<SlotStatus, Set<SlotStatus>> ALLOWED_TRANSITIONS = new HashMap<>();

    static {
        ALLOWED_TRANSITIONS.put(SlotStatus.UNAVAILABLE , Set.of(SlotStatus.AVAILABLE));
        ALLOWED_TRANSITIONS.put(SlotStatus.AVAILABLE ,
                                Set.of(SlotStatus.BOOKED , SlotStatus.WALKIN , SlotStatus.BLOCKED));
        ALLOWED_TRANSITIONS.put(SlotStatus.BOOKED ,
                                Set.of(SlotStatus.ARRIVED , SlotStatus.COMPLETED , SlotStatus.NO_SHOW ,
                                       SlotStatus.CANCELLED));
        ALLOWED_TRANSITIONS.put(SlotStatus.BLOCKED , Set.of(SlotStatus.AVAILABLE));
        ALLOWED_TRANSITIONS.put(SlotStatus.RESERVED , Set.of(SlotStatus.BOOKED , SlotStatus.AVAILABLE));
    }

    public DoctorSlotServiceImpl ( DoctorRepository doctorRepository , AvailabilityRepository availabilityRepository ) {
        this.doctorRepository = doctorRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public void updateSlotStatus ( Long slotId , SlotStatus newStatus ) {
        Availability slot = availabilityRepository.findById(slotId)
                                                  .orElseThrow(() -> new RuntimeException("Slot not found"));

        SlotStatus currentStatus = slot.getStatus();

        // Check if transition is valid using the map
        if (ALLOWED_TRANSITIONS.containsKey(currentStatus) && ALLOWED_TRANSITIONS.get(currentStatus)
                                                                                 .contains(newStatus)) {
            slot.setStatus(newStatus);
            availabilityRepository.save(slot);
        } else {
            throw new RuntimeException("Invalid slot status transition from " + currentStatus + " to " + newStatus);
        }
    }

    @Override
    @Transactional
    public List<DoctorSlotResponse> generateOrUpdateDoctorSlots ( Long doctorId , String startTimeStr , String endTimeStr , List<String> days , List<String> dates ) {
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Convert string times to LocalTime
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);
        int slotDuration = 10; // Each slot is 10 minutes

        // If "ALL" is passed, replace it with all 7 days of the week
        if (days.contains("ALL")) {
            days = Arrays.asList("Monday" , "Tuesday" , "Wednesday" , "Thursday" , "Friday" , "Saturday" , "Sunday");
        }

        // Convert string dates to LocalDate
        List<LocalDate> slotDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (String dateStr : dates) {
            slotDates.add(LocalDate.parse(dateStr , formatter));
        }

        List<SlotResponse> allSlots = new ArrayList<>();
        List<Availability> slotsToSave = new ArrayList<>();

        for (int i = 0 ; i < days.size() ; i++) {
            String day = days.get(i);
            LocalDate date = (i < slotDates.size())?slotDates.get(i):LocalDate.now();

            LocalTime currentTime = startTime;
            while (!currentTime.isAfter(endTime.minusMinutes(slotDuration))) {
                LocalTime slotEnd = currentTime.plusMinutes(slotDuration);

                // Check if slot exists
                Optional<Availability> existingSlot = availabilityRepository.findExistingSlot(doctorId , day , date ,
                                                                                              currentTime.toString() ,
                                                                                              slotEnd.toString());
                if (existingSlot.isPresent()) {
                    // Update existing slot
                    Availability slot = existingSlot.get();
                    slot.setStartTime(currentTime.toString());
                    slot.setEndTime(slotEnd.toString());
                    slot.setStatus(SlotStatus.AVAILABLE);
                    slotsToSave.add(slot);
                } else {
                    // Create new slot
                    Availability newSlot = Availability.builder()
                                                       .doctor(doctor)
                                                       .day(day)
                                                       .date(date)
                                                       .startTime(currentTime.toString())
                                                       .endTime(slotEnd.toString())
                                                       .status(SlotStatus.AVAILABLE)
                                                       .build();
                    slotsToSave.add(newSlot);
                }

                currentTime = slotEnd;
            }
        }

        // Save all new & updated slots
        List<Availability> savedSlots = availabilityRepository.saveAll(slotsToSave);

        // Convert saved slots to response format
        savedSlots.forEach(slot -> allSlots.add(SlotResponse.builder()
                                                            .slotId(slot.getId())
                                                            .day(slot.getDay())
                                                            .date(slot.getDate().toString())
                                                            .startTime(slot.getStartTime())
                                                            .endTime(slot.getEndTime())
                                                            .status(slot.getStatus())
                                                            .build()));

        return List.of(DoctorSlotResponse.builder()
                                         .doctorId(doctor.getId())
                                         .doctorName(doctor.getName())
                                         .department(doctor.getDepartment())
                                         .slots(allSlots)
                                         .build());
    }


}