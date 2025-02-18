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

    @Override // This method is overriding a method from a superclass or an interface.
    @Transactional // This annotation makes sure that all database operations in this method are part of one transaction.
    public List<DoctorSlotResponse> generateOrUpdateDoctorSlots(Long doctorId, String startTimeStr, String endTimeStr, List<String> days, List<String> dates) {

        // Look up the doctor by the given doctorId in the database.
        // If the doctor is not found, throw a RuntimeException with the message "Doctor not found".
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Convert the provided start and end times (which are strings) into LocalTime objects.
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);

        int slotDuration = 10; // Define that each time slot will be 10 minutes long.

        // Check if the list of days includes the special keyword "ALL".
        // If it does, replace the list with all days of the week.
        if (days.contains("ALL")) {
            days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        }

        // Prepare a list to hold LocalDate objects converted from the provided date strings.
        List<LocalDate> slotDates = new ArrayList<>();

        // Define the format that the date strings are expected to be in (e.g., "2025-02-18").
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Loop through each date string provided in the 'dates' list.
        for (String dateStr : dates) {
            // Convert the date string into a LocalDate object using the specified format.
            LocalDate date = LocalDate.parse(dateStr, formatter);
            // Add the converted date to our list of slot dates.
            slotDates.add(date);
        }

        // Create a list to store the responses for each slot, which will be sent back to the caller.
        List<SlotResponse> allSlots = new ArrayList<>();

        // Create a list to hold new or updated slot objects that we plan to save to the database.
        List<Availability> slotsToSave = new ArrayList<>();

        // Loop through each day provided in the 'days' list.
        for (int i = 0; i < days.size(); i++) {
            // Get the current day (e.g., "Monday", "Tuesday") from the list.
            String day = days.get(i);

            // Determine the date that corresponds to the current day:
            // If a date is provided in the slotDates list at the same position, use it.
            // Otherwise, use today's date as a default.
            LocalDate date;
            if (i < slotDates.size()) {
                date = slotDates.get(i);
            } else {
                date = LocalDate.now();
            }

            // Start the time pointer at the startTime, which will be used to create slots.
            LocalTime currentTime = startTime;

            // Create time slots while ensuring that each slot (of slotDuration minutes)
            // will end before or exactly at the endTime.
            while (!currentTime.isAfter(endTime.minusMinutes(slotDuration))) {

                // Calculate the end time for the current slot by adding the slot duration to currentTime.
                LocalTime slotEnd = currentTime.plusMinutes(slotDuration);

                // Check if a slot already exists in the database for this doctor on the given day and date,
                // with the specific start and end times.
                Optional<Availability> existingSlot = availabilityRepository.findExistingSlot(
                        doctorId,
                        day,
                        date,
                        currentTime.toString(),
                        slotEnd.toString()
                );

                // If an existing slot is found...
                if (existingSlot.isPresent()) {
                    // Retrieve the existing slot.
                    Availability slot = existingSlot.get();
                    // Update the start time (ensuring it matches our currentTime).
                    slot.setStartTime(currentTime.toString());
                    // Update the end time.
                    slot.setEndTime(slotEnd.toString());
                    // Mark the slot as AVAILABLE.
                    slot.setStatus(SlotStatus.AVAILABLE);
                    // Add the updated slot to our list for saving.
                    slotsToSave.add(slot);
                } else {
                    // If no slot exists, create a new one using the builder pattern.
                    Availability newSlot = Availability.builder()
                                                       .doctor(doctor)                    // Set the doctor associated with this slot.
                                                       .day(day)                          // Set the day (e.g., "Monday") for this slot.
                                                       .date(date)                        // Set the date for the slot.
                                                       .startTime(currentTime.toString()) // Set the slot's start time.
                                                       .endTime(slotEnd.toString())       // Set the slot's end time.
                                                       .status(SlotStatus.AVAILABLE)      // Set the slot's status to AVAILABLE.
                                                       .build();                          // Create the Availability object.
                    // Add the new slot to our list for saving.
                    slotsToSave.add(newSlot);
                }

                // Move the currentTime pointer to the end of the current slot,
                // so the next loop iteration will create the next slot.
                currentTime = slotEnd;
            }
        }

        // Save all the new and updated slots to the database.
        List<Availability> savedSlots = availabilityRepository.saveAll(slotsToSave);

        // Convert each saved slot into a SlotResponse object (a format suitable for sending back to the client).
        savedSlots.forEach(slot -> allSlots.add(SlotResponse.builder()
                                                            .slotId(slot.getId())
                                                            .day(slot.getDay())
                                                            .date(slot.getDate().toString())
                                                            .startTime(slot.getStartTime())
                                                            .endTime(slot.getEndTime())
                                                            .status(slot.getStatus())
                                                            .build()));

        // Build the final response containing the doctor's details and the list of slot responses,
        // and return it as a single-element list.
        return List.of(DoctorSlotResponse.builder()
                                         .doctorId(doctor.getId())
                                         .doctorName(doctor.getName())
                                         .department(doctor.getDepartment())
                                         .slots(allSlots)
                                         .build());
    }


}