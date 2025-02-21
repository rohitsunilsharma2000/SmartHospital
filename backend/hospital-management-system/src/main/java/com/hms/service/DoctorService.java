package com.hms.service;

import com.hms.dto.DoctorFeeRequest;
import com.hms.dto.DoctorFeeResponse;
import com.hms.dto.DoctorRequest;
import com.hms.dto.DoctorResponse;
import com.hms.exception.DoctorAlreadyExistsException;
import com.hms.modal.Doctor;
import com.hms.modal.DoctorFee;
import com.hms.repository.DoctorFeeRepository;
import com.hms.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorFeeRepository doctorFeeRepository;
    public DoctorService ( DoctorRepository doctorRepository , DoctorFeeRepository doctorFeeRepository ) {
        this.doctorRepository = doctorRepository;
        this.doctorFeeRepository = doctorFeeRepository;
    }

    public Doctor createDoctor( DoctorRequest request) {
        if (doctorRepository.existsByDocLicence(request.getDocLicence())) {
            throw new DoctorAlreadyExistsException("Doctor with licence " + request.getDocLicence() + " already exists");
        }

        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setSpecialty(request.getSpecialty());
        doctor.setDocLicence(request.getDocLicence());

        //Saves all fees along with the doctor during creation.
        if (request.getFees() != null && !request.getFees().isEmpty()) {
            List<DoctorFee> fees = request.getFees().stream().map(feeRequest -> {
                DoctorFee fee = new DoctorFee();
                fee.setConsultationType(feeRequest.getConsultationType());
                fee.setUnitPrice(feeRequest.getUnitPrice());
                fee.setDoctor(doctor);
                return fee;
            }).toList();
            doctor.setFees(fees);
        }

        return doctorRepository.save(doctor);
    }

    public List<DoctorResponse> searchDoctors( String name, Long id) {
        List<Doctor> doctors;
        if (id != null) {
            doctors = doctorRepository.findById(id).map(List::of).orElse(List.of());
        } else if (name != null) {
            doctors = doctorRepository.findByNameContainingIgnoreCase(name);
        } else {
            doctors = doctorRepository.findAll();
        }
        return doctors.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setName(request.getName());
        doctor.setSpecialty(request.getSpecialty());
        doctor.setDocLicence(request.getDocLicence());
        return mapToResponse(doctorRepository.save(doctor));
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    private DoctorResponse mapToResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setSpecialty(doctor.getSpecialty());
        response.setDocLicence(doctor.getDocLicence());
        return response;
    }

}
