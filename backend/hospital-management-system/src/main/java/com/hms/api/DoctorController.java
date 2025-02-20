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

    public DoctorController ( DoctorService doctorService ) {
        this.doctorService = doctorService;
    }

    @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor (
            @RequestBody CreateDoctorRequest request ,
            @RequestParam String startTime ,
            @RequestParam String endTime ,
            @RequestParam List<String> days ,
            @RequestParam List<String> dates ) {

        Doctor doctor = doctorService.registerDoctor(request , startTime , endTime , days , dates);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("/registerMultiple")
    public ResponseEntity<List<Doctor>> registerMultipleDoctors (
            @RequestBody List<CreateDoctorRequest> requests ,
            @RequestParam String startTime ,
            @RequestParam String endTime ,
            @RequestParam List<String> days ,
            @RequestParam List<String> dates ) {

        List<Doctor> doctors = doctorService.registerMultipleDoctors(requests , startTime , endTime , days , dates);
        return ResponseEntity.ok(doctors);
    }


    // 1. Get All Doctors
    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponse>> getAllDoctorsWithSlots () {
        List<DoctorResponse> doctors = doctorService.getAllDoctorsWithSlots();
        return ResponseEntity.ok(doctors);
    }

    // 2. Get Doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById ( @PathVariable Long id ) {
        DoctorResponse doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/searchById")
    public ResponseEntity<List<DoctorResponse>> searchDoctorsById ( @RequestParam String query ) {
        List<DoctorResponse> doctors = doctorService.searchDoctorsById(query);
        return ResponseEntity.ok(doctors);
    }

    // 3. Get Doctor by Name
    @GetMapping("/search")
    public ResponseEntity<List<DoctorResponse>> searchDoctorsByName ( @RequestParam String query ) {
        List<DoctorResponse> doctors = doctorService.searchDoctorsByName(query);
        return ResponseEntity.ok(doctors);
    }


}
