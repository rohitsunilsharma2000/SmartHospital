package com.hms.api;
import com.hms.dto.CreateDoctorRequest;
import com.hms.modal.Doctor;
import com.hms.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor(
            @RequestBody CreateDoctorRequest request,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam List<String> days,
            @RequestParam List<String> dates) {

        Doctor doctor = doctorService.registerDoctor(request, startTime, endTime, days, dates);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("/registerMultiple")
    public ResponseEntity<List<Doctor>> registerMultipleDoctors(
            @RequestBody List<CreateDoctorRequest> requests,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam List<String> days,
            @RequestParam List<String> dates) {

        List<Doctor> doctors = doctorService.registerMultipleDoctors(requests, startTime, endTime, days, dates);
        return ResponseEntity.ok(doctors);
    }
}
