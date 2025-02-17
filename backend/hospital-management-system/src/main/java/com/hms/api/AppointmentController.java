package com.hms.api;


import com.hms.dto.AppointmentRequest;
import com.hms.dto.AppointmentResponse;
import com.hms.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> bookAppointment( @RequestBody AppointmentRequest request) {
        AppointmentResponse appointment = appointmentService.bookAppointment(request);
        return ResponseEntity.ok(appointment);
    }
}
