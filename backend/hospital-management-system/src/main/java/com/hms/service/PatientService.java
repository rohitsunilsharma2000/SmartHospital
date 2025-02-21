package com.hms.service;

import com.hms.dto.PatientRequest;
import com.hms.dto.PatientResponse;
import com.hms.modal.Patient;
import com.hms.modal.PatientId;
import com.hms.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    // ✅ Create patient (with duplicate check based on composite key)
    public PatientResponse createPatient( PatientRequest request) {
        PatientId patientId = new PatientId(request.getMobile(), request.getHospId());

        if (patientRepository.existsById(patientId)) {
            throw new RuntimeException("Patient already exists with mobile: " + request.getMobile() + " and hospId: " + request.getHospId());
        }

        Patient patient = mapToEntity(request);
        return mapToResponse(patientRepository.save(patient));
    }


    // ✅ Update patient (if exists)
    public PatientResponse updatePatient(PatientRequest request) {
        PatientId patientId = new PatientId(request.getMobile(), request.getHospId());
        Patient patient = patientRepository.findById(patientId)
                                           .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient = mapToEntity(request);
        return mapToResponse(patientRepository.save(patient));
    }

    // ✅ Delete patient by composite key
    public void deletePatient(String mobile, String hospId) {
        PatientId patientId = new PatientId(mobile, hospId);
        patientRepository.deleteById(patientId);
    }

    // ✅ Utility to map DTO to entity
    private Patient mapToEntity(PatientRequest request) {
        Patient patient = new Patient();
        patient.setId(new PatientId(request.getMobile(), request.getHospId()));
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setAge(request.getAge());
        patient.setSex(request.getSex());
        patient.setRegistered(request.getRegistered());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setMotherTongue(request.getMotherTongue());
        patient.setPatientTag(request.getPatientTag());
        patient.setLandline(request.getLandline());
        patient.setFileNumber(request.getFileNumber());
        patient.setGovtId(request.getGovtId());
        patient.setMaritalStatus(request.getMaritalStatus());
        patient.setGovtIdNumber(request.getGovtIdNumber());
        patient.setEmail(request.getEmail());
        patient.setOtherHospitalIds(request.getOtherHospitalIds());
        patient.setReferrerType(request.getReferrerType());
        patient.setReferrerName(request.getReferrerName());
        patient.setReferrerNumber(request.getReferrerNumber());
        patient.setReferrerEmail(request.getReferrerEmail());
        patient.setAddress(request.getAddress());
        patient.setArea(request.getArea());
        patient.setCity(request.getCity());
        patient.setPinCode(request.getPinCode());
        patient.setState(request.getState());
        patient.setCountry(request.getCountry());
        patient.setNationality(request.getNationality());
        patient.setPatientPhoto(request.getPatientPhoto());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setFatherName(request.getFatherName());
        patient.setMotherName(request.getMotherName());
        patient.setSpouseName(request.getSpouseName());
        patient.setAlternateContact(request.getAlternateContact());
        patient.setEducation(request.getEducation());
        patient.setOccupation(request.getOccupation());
        patient.setReligion(request.getReligion());
        patient.setIvrLanguage(request.getIvrLanguage());
        patient.setBirthWeight(request.getBirthWeight());
        patient.setNotes(request.getNotes());

        return patient;
    }

    // ✅ Utility to map entity to response
    private PatientResponse mapToResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setMobile(patient.getId().getMobile());
        response.setHospId(patient.getId().getHospId());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setEmail(patient.getEmail());
        return response;
    }

    // ✅ Search patients by ID, name, or composite key (type-ahead)
    public List<PatientResponse> searchPatients(String mobile, String hospId, String name) {
        List<Patient> patients;

        if (mobile != null && hospId != null) {
            // ✅ Search by composite key using built-in JPA method
            Optional<Patient> patient = patientRepository.findByIdMobileAndIdHospId(mobile, hospId);
            patients = patient.map(List::of).orElse(List.of());
        } else if (name != null) {
            // ✅ Search by name (firstName or lastName) - Type-ahead
            patients = patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
        } else if (mobile != null || hospId != null) {
            // ✅ Type-ahead search by mobile or hospital ID
            patients = patientRepository.findByIdMobileContainingIgnoreCaseOrIdHospIdContainingIgnoreCase(
                    mobile != null ? mobile : "", hospId != null ? hospId : "");
        } else {
            // ✅ Return all patients if no filter is provided
            patients = patientRepository.findAll();
        }

        return patients.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

}
