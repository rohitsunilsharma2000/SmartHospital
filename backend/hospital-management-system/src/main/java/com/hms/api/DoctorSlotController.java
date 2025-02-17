package com.hms.api;

import com.hms.dto.DoctorSlotResponse;
import com.hms.enums.SlotStatus;
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

    @PostMapping("/generateOrUpdate")
    public ResponseEntity<List<DoctorSlotResponse>> generateOrUpdateDoctorSlots(
            @PathVariable Long doctorId,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam List<String> days,
            @RequestParam List<String> dates) {

        List<DoctorSlotResponse> doctorSlots = doctorSlotService.generateOrUpdateDoctorSlots(doctorId, startTime, endTime, days, dates);
        return ResponseEntity.ok(doctorSlots);
    }

    @PutMapping("/{slotId}/status")
    public ResponseEntity<String> updateSlotStatus(
            @PathVariable Long slotId,
            @RequestParam SlotStatus status) {

        doctorSlotService.updateSlotStatus(slotId, status);
        return ResponseEntity.ok("Slot status updated successfully.");
    }
}
