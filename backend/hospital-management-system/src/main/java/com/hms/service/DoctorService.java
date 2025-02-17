package com.hms.service;

import com.hms.dto.CreateDoctorRequest;
import com.hms.modal.Doctor;
import java.util.List;

public interface DoctorService {
    Doctor registerDoctor(CreateDoctorRequest request);
    List<Doctor> registerMultipleDoctors(List<CreateDoctorRequest> requests);
}
