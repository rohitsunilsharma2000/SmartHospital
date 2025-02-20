package com.hms.service;

import com.hms.api.DoctorResponse;
import com.hms.dto.CreateDoctorRequest;
import com.hms.modal.Doctor;
import java.util.List;

public interface DoctorService {
    Doctor registerDoctor(CreateDoctorRequest request, String startTime, String endTime, List<String> days, List<String> dates);
    List<Doctor> registerMultipleDoctors(List<CreateDoctorRequest> requests, String startTime, String endTime, List<String> days, List<String> dates);

    List<DoctorResponse> getAllDoctorsWithSlots();

    DoctorResponse getDoctorById(Long id);

    List<DoctorResponse> searchDoctorsByName ( String query );

    public List<DoctorResponse> searchDoctorsById(String query) ;
}
