package com.hms.api;

import com.hms.dto.DoctorSlotResponse;
import com.hms.service.DoctorSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors/{doctorId}/slots")
public class DoctorSlotController {

    private final DoctorSlotService doctorSlotService;

    public DoctorSlotController(DoctorSlotService doctorSlotService) {
        this.doctorSlotService = doctorSlotService;
    }




    @GetMapping
    public ResponseEntity<List<DoctorSlotResponse>> getDoctorSlots(
            @PathVariable Long doctorId,
            @RequestParam(required = false) Integer dayOffset,
            @RequestParam(required = false) String day) {

        List<DoctorSlotResponse> doctorSlots = doctorSlotService.getDoctorSlots(doctorId, dayOffset, day);
        return ResponseEntity.ok(doctorSlots);
    }
}
