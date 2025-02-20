package com.hms.api;


import com.hms.dto.AppointmentRequest;
import com.hms.dto.AppointmentResponse;
import com.hms.modal.Availability;
import com.hms.repository.AvailabilityRepository;
import com.hms.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AvailabilityRepository availabilityRepository;

    public AppointmentController ( AppointmentService appointmentService , AvailabilityRepository availabilityRepository ) {
        this.appointmentService = appointmentService;
        this.availabilityRepository = availabilityRepository;
    }


    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> bookAppointmentV2( @RequestBody AppointmentRequest request) {
        AppointmentResponse appointment = appointmentService.bookAppointment(request);
        return ResponseEntity.ok(appointment);
    }


    // ✅ Book Appointment
    @PostMapping("/book-v2")
    public ResponseEntity<AppointmentResponse> bookAppointment(@RequestBody AppointmentRequest request) {
        log.info("Booking appointment: {}", request);

        // 1. ✅ Fetch Slot
        Availability slot = availabilityRepository.findById(request.getSlotId())
                                                  .orElseThrow(() -> new RuntimeException("Slot not found"));

        // 2. ✅ Call Service Method
        AppointmentResponse response = appointmentService.bookAppointment(request, slot);

        log.info("Appointment booked successfully: {}", response.getId());
        return ResponseEntity.ok(response);
    }


}
