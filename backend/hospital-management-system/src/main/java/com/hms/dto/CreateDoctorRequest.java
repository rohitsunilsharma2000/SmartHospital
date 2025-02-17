package com.hms.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateDoctorRequest {
    private String name;
    private String department;
    private Boolean outPatient;
    private String busyTime;
    private List<String> notificationSchedules;
    private List<DayAvailabilityRequest> availability; // New field
}
