package com.hms.service;



import com.hms.dto.CreateAvailabilityRequest;
import com.hms.modal.Availability;
import java.util.List;

public interface AvailabilityService {
    List<Availability> addAvailability(Long doctorId, CreateAvailabilityRequest request);  // Fix return type
    List<Availability> getAvailabilitiesForDoctor(Long doctorId);
    Availability updateAvailability(Long doctorId, Long availabilityId, CreateAvailabilityRequest request);
    void deleteAvailability(Long doctorId, Long availabilityId);
}
