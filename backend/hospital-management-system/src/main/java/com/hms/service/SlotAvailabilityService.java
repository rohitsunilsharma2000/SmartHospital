package com.hms.service;

import com.hms.dto.SlotAvailabilityRequest;
import com.hms.dto.SubSlotDTO;
import com.hms.enums.SlotStatus;
import com.hms.modal.Doctor;
import com.hms.modal.SlotAvailability;
import com.hms.repository.DoctorRepository;
import com.hms.repository.SlotAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlotAvailabilityService {

    private final SlotAvailabilityRepository slotAvailabilityRepository;
    private final DoctorRepository doctorRepository;

    /**
     * ✅ ** CreateSlotsFunctionalities **
     *
     * 1. **Doctor Validation:** Ensures the doctor exists, otherwise throws an error.
     * 2. **Delete Old Slots:** Deletes old slots within the provided date range before creating new ones.
     * 3. **Slot Creation:** Creates slots based on provided `subSlots` or generates default slots if none are provided.
     * 4. **Flexible Time Parsing:** Allows parsing both "9:00AM" and "9:00 AM" formats.
     * 5. **10-Minute Intervals:** Generates default slots in 10-minute intervals between `startTime` and `endTime`.
     * 6. **Slot Number Generation:* Generates unique slot numbers using doctor ID, date, and start time.
     * 7. **Logging:** Logs key steps such as slot deletion, slot processing, slot creation, and totals.
     * 8. **Bulk Save:** Saves all created slots in a single `saveAll` call for efficiency.
     *
     */

    public List<SlotAvailability> createSlots ( Long doctorId , SlotAvailabilityRequest request ) {
        // ✅ Logging the start of the slot creation process
        log.info("Starting createSlots process for doctorId: {}" , doctorId);

        // ✅ 1. Doctor Validation: Ensures the doctor exists, otherwise throws an error
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> {
                                            log.error("Doctor not found for doctorId: {}" , doctorId);
                                            return new RuntimeException("Doctor not found");
                                        });

        // ✅ 2. Delete Old Slots: Deletes old slots within the provided date range before creating new ones
        log.info("Deleting old slots for doctorId: {} within date range {} to {}" , doctorId , request.getStartDate() ,
                 request.getEndDate());
        slotAvailabilityRepository.deleteByDoctorIdAndDateRange(doctorId , request.getStartDate() ,
                                                                request.getEndDate());

        List<SlotAvailability> slots = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());

        // ✅ 3. Slot Creation: Loop through each date within the specified range
        for (LocalDate date = startDate ; !date.isAfter(endDate) ; date = date.plusDays(1)) {

            // ✅ 4. Sub-Slots Generation: Use provided sub-slots or generate default slots if none are provided
            List<SubSlotDTO> subSlots = request.getSubSlots() != null && !request.getSubSlots().isEmpty()
                    ?request.getSubSlots()
                    :generateDefaultSubSlots(doctor.getId() , date , request.getStartTime() , request.getEndTime());

            log.info("Processing {} sub-slots for date {}" , subSlots.size() , date);

            // ✅ 5. Create Each Slot: Loop through each sub-slot and create a SlotAvailability object
            for (SubSlotDTO subSlotDTO : subSlots) {
                String slotNumber = generateSlotNumber(doctor.getId() , date , subSlotDTO.getTime().split(" ")[0]);
                log.debug("Creating new slot: {}" , slotNumber);

                // ✅ 6. Slot Number Generation: Generate a unique slot number using doctor ID, date, and start time
                SlotAvailability slot = new SlotAvailability();
                slot.setDoctor(doctor);
                slot.setSlotNumber(slotNumber);
                slot.setStatus(SlotStatus.valueOf(subSlotDTO.getStatus())); // ✅ Slot status is set (AVAILABLE/BOOKED)
                slot.setTime(subSlotDTO.getTime()); // ✅ Store the time range as a string (e.g., "9:00 AM to 9:10 AM")
                slot.setStartDate(date.toString()); // ✅ Store the start date as a string
                slot.setEndDate(
                        date.toString());   // ✅ Store the end date as a string (same as startDate since it's daily slots)
                slots.add(slot); // ✅ Add the slot to the list
            }
        }

        // ✅ 7. Bulk Save: Save all created slots in a single saveAll call for efficiency
        List<SlotAvailability> savedSlots = slotAvailabilityRepository.saveAll(slots);
        log.info("Slots created: {}" , savedSlots.size());

        return savedSlots; // ✅ Return the list of saved slots
    }


    private List<SubSlotDTO> generateDefaultSubSlots ( Long doctorId , LocalDate date , String startTime , String endTime ) {
        List<SubSlotDTO> subSlots = new ArrayList<>();

        // ✅ 8. Flexible Time Parsing: Allows parsing both "9:00AM" and "9:00 AM" formats (case-insensitive)
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("h:mm")
                .optionalStart().appendLiteral(' ').optionalEnd() // Allow optional space before AM/PM
                .appendPattern("a")
                .toFormatter();

        // ✅ Parse startTime and endTime into LocalTime objects
        LocalTime start = LocalTime.parse(startTime.toUpperCase() , timeFormatter);
        LocalTime end = LocalTime.parse(endTime.toUpperCase() , timeFormatter);

        // ✅ 9. Generate 10-Minute Interval Sub-Slots Between startTime and endTime
        while (start.isBefore(end)) {
            LocalTime next = start.plusMinutes(10); // ✅ Each sub-slot is 10 minutes long
            String timeRange = start.format(timeFormatter) + " to " + next.format(
                    timeFormatter); // ✅ Time range as a string

            // ✅ 10. Create SubSlotDTO: Store slot number, status, and time range
            SubSlotDTO subSlotDTO = new SubSlotDTO();
            subSlotDTO.setSlotNumber(
                    generateSlotNumber(doctorId , date , start.format(timeFormatter))); // ✅ Generate slot number
            subSlotDTO.setStatus(SlotStatus.AVAILABLE.name()); // ✅ Default status is AVAILABLE
            subSlotDTO.setTime(timeRange); // ✅ Store the time range as a string

            subSlots.add(subSlotDTO); // ✅ Add the sub-slot to the list

            start = next; // ✅ Move to the next 10-minute interval
        }

        return subSlots; // ✅ Return the list of generated sub-slots
    }


    private String generateSlotNumber ( Long doctorId , LocalDate date , String time ) {
        return doctorId + "_" + date.toString() + "_" + time.split(" ")[0];
    }

    public List<SlotAvailability> getSlotsByDoctorId ( Long doctorId ) {
        return slotAvailabilityRepository.findByDoctorId(doctorId);
    }

    public void deleteSlot ( Long id ) {
        slotAvailabilityRepository.deleteById(id);
    }

}
