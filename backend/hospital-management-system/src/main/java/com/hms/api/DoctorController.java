package com.hms.api;


import com.hms.modal.Doctor;
import com.hms.service.DoctorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {


    private DoctorService doctorService;


    public DoctorController ( DoctorService doctorService ) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public Doctor createDoctor ( @RequestBody Doctor doctor ) {
        Doctor createdDoctor = doctorService.createDoctor((doctor));
        return createdDoctor;
    }

    @GetMapping("/{id}")
    public Doctor getDoctor ( @PathVariable Long id ) {
        Doctor doctor = doctorService.getDoctor(id);
        return doctor;
    }
}
