package com.hms.api;
import com.hms.dto.CreateDoctorRequest;
import com.hms.modal.Doctor;
import com.hms.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;


@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Register a single doctor
    @PostMapping
    public ResponseEntity<Doctor> registerDoctor(@RequestBody CreateDoctorRequest request) {
        Doctor doctor = doctorService.registerDoctor(request);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Doctor>> registerMultipleDoctors(@RequestBody List<CreateDoctorRequest> requests) {
        List<Doctor> doctors = doctorService.registerMultipleDoctors(requests);
        return ResponseEntity.ok(doctors);
    }



}
