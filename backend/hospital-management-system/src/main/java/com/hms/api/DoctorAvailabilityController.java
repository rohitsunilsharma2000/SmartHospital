package com.hms.api;


import com.hms.dto.CreateAvailabilityRequest;
import com.hms.modal.Availability;
import com.hms.service.AvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors/{doctorId}/availabilities")
public class DoctorAvailabilityController {

    private final AvailabilityService availabilityService;

    public DoctorAvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    // Add a new availability slot for a doctor
    @PostMapping
    public ResponseEntity<List<Availability> > addAvailability(@PathVariable Long doctorId,
                                                        @RequestBody CreateAvailabilityRequest request) {
        List<Availability>  availability = availabilityService.addAvailability(doctorId, request);
        return ResponseEntity.ok(availability);
    }

    // Retrieve all availability slots for a doctor
    @GetMapping
    public ResponseEntity<List<Availability>> getAvailabilities(@PathVariable Long doctorId) {
        List<Availability> availabilities = availabilityService.getAvailabilitiesForDoctor(doctorId);
        return ResponseEntity.ok(availabilities);
    }

    // Update a specific availability slot
    @PutMapping("/{availabilityId}")
    public ResponseEntity<Availability> updateAvailability(@PathVariable Long doctorId,
                                                           @PathVariable Long availabilityId,
                                                           @RequestBody CreateAvailabilityRequest request) {
        Availability updatedAvailability = availabilityService.updateAvailability(doctorId, availabilityId, request);
        return ResponseEntity.ok(updatedAvailability);
    }

    // Delete a specific availability slot
    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long doctorId,
                                                   @PathVariable Long availabilityId) {
        availabilityService.deleteAvailability(doctorId, availabilityId);
        return ResponseEntity.noContent().build();
    }
}
