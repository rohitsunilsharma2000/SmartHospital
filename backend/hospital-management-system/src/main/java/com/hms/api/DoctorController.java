package com.hms.api;


import com.hms.dto.DoctorRequest;
import com.hms.dto.DoctorResponse;
import com.hms.dto.SlotAvailabilityRequest;
import com.hms.modal.Doctor;
import com.hms.modal.SlotAvailability;
import com.hms.service.DoctorService;
import com.hms.service.SlotAvailabilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {


    private final DoctorService doctorService;
    private final SlotAvailabilityService slotAvailabilityService;


    public DoctorController ( DoctorService doctorService , SlotAvailabilityService slotAvailabilityService ) {
        this.doctorService = doctorService;
        this.slotAvailabilityService = slotAvailabilityService;
    }

    @PostMapping
    public ResponseEntity<String> createDoctor ( @RequestBody DoctorRequest request ) {
        Doctor doctor = doctorService.createDoctor(request);
        return ResponseEntity.ok("Doctor created with ID: " + doctor.getId());
    }

    @PostMapping("/{doctorId}/slots")
    public ResponseEntity<String> createSlots(@PathVariable Long doctorId, @RequestBody SlotAvailabilityRequest request) {
        log.info("Received request to create or update slots for doctorId: {}", doctorId);
        log.info("Request details: startDate = {}, endDate = {}, startTime = {}, endTime = {}",
                    request.getStartDate(), request.getEndDate(), request.getStartTime(), request.getEndTime());

        List<SlotAvailability> slots = slotAvailabilityService.createSlots(doctorId, request);

        log.info("Slots created successfully. Total slots: {}", slots.size());

        return ResponseEntity.ok(String.format("Slots created successfully. Total slots: %d", slots.size()));
    }
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> searchDoctors (
            @RequestParam(required = false) String name ,
            @RequestParam(required = false) Long id ) {
        return ResponseEntity.ok(doctorService.searchDoctors(name , id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor ( @PathVariable Long id , @RequestBody DoctorRequest request ) {
        return ResponseEntity.ok(doctorService.updateDoctor(id , request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor ( @PathVariable Long id ) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    @GetMapping("/{doctorId}/slots")
    public ResponseEntity<List<SlotAvailability>> getSlotsByDoctorId ( @PathVariable Long doctorId ) {
        return ResponseEntity.ok(slotAvailabilityService.getSlotsByDoctorId(doctorId));
    }

//    @DeleteMapping("/slots/{id}")
//    public ResponseEntity<String> deleteSlot ( @PathVariable Long id ) {
//        slotAvailabilityService.deleteSlot(id);
//        return ResponseEntity.ok("Slot deleted successfully.");
//    }
}

