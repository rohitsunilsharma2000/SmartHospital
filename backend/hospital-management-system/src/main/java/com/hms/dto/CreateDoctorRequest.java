package com.hms.dto;

import java.util.List;

public class CreateDoctorRequest {
    private String name;
    private String department;
    private Boolean outPatient;
    private String busyTime;
    private List<String> notificationSchedules;
}
