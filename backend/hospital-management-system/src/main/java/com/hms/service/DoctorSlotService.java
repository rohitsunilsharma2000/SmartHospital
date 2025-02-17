package com.hms.service;


import com.hms.dto.DoctorSlotResponse;
import com.hms.modal.Availability;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface DoctorSlotService {

    /**
     * Fetches the list of doctors along with their available slots.
     * Supports filtering by:
     *  - Absolute day name (Monday-Sunday)
     *  - Relative days (Today, Next, Next+N)
     *
     * @param dayOffset The number of days from today (0 = today, 1 = next day, N = next N days)
     * @param dayName The specific day of the week (e.g., "Monday", "Tuesday")
     * @return A list of doctors with their respective available slots
     */
    List<DoctorSlotResponse> getDoctorSlots(Long doctorId, Integer dayOffset, String dayName);


}
