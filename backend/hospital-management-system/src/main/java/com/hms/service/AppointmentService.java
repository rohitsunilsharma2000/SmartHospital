package com.hms.service;


import com.hms.dto.AppointmentRequest;
import com.hms.dto.AppointmentResponse;
import com.hms.modal.Availability;

public interface AppointmentService {
    AppointmentResponse bookAppointment ( AppointmentRequest request );

    AppointmentResponse bookAppointment ( AppointmentRequest request , Availability slot );

}
