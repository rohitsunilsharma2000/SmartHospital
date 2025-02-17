package com.hms.service;


import com.hms.dto.AppointmentRequest;
import com.hms.dto.AppointmentResponse;

public interface AppointmentService {
    AppointmentResponse bookAppointment( AppointmentRequest request);
}
