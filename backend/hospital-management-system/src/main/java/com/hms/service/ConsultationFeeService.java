package com.hms.service;

import com.hms.dto.ConsultationFeeRequest;
import com.hms.dto.ConsultationFeeResponse;
import com.hms.modal.ConsultationFee;
import com.hms.modal.Doctor;
import com.hms.repository.ConsultationFeeRepository;
import com.hms.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConsultationFeeService {

    private final ConsultationFeeRepository consultationFeeRepository;
    private final DoctorRepository doctorRepository;

    public ConsultationFeeService(ConsultationFeeRepository consultationFeeRepository, DoctorRepository doctorRepository) {
        this.consultationFeeRepository = consultationFeeRepository;
        this.doctorRepository = doctorRepository;
    }

    // Fetch all consultation fees for a doctor
    public List<ConsultationFeeResponse> getConsultationFeesForDoctor( Long doctorId) {
        List<ConsultationFee> fees = consultationFeeRepository.findByDoctorId(doctorId);
        return fees.stream().map(ConsultationFeeResponse::fromEntity).toList();
    }

    // Typeahead search for consultation types
    public List<ConsultationFeeResponse> searchConsultations(String keyword) {
        List<ConsultationFee> fees = consultationFeeRepository.findByConsultationTypeContainingIgnoreCase(keyword);
        return fees.stream().map(ConsultationFeeResponse::fromEntity).toList();
    }

    // Add a new consultation fee for a doctor
    public ConsultationFeeResponse addConsultationFee(Long doctorId, String consultationType, Double unitPrice) {
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        ConsultationFee fee = ConsultationFee.builder()
                                             .doctor(doctor)
                                             .consultationType(consultationType)
                                             .unitPrice(unitPrice)
                                             .build();

        fee = consultationFeeRepository.save(fee);
        return ConsultationFeeResponse.fromEntity(fee);
    }

    // Bulk Add Consultation Fees for a Doctor
    public List<ConsultationFeeResponse> addBulkConsultationFees(Long doctorId, List<ConsultationFeeRequest> fees) {
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<ConsultationFee> feeEntities = fees.stream()
                                                .map(fee -> ConsultationFee.builder()
                                                                           .doctor(doctor)
                                                                           .consultationType(fee.getConsultationType())
                                                                           .unitPrice(fee.getUnitPrice())
                                                                           .build())
                                                .toList();

        consultationFeeRepository.saveAll(feeEntities);

        return feeEntities.stream().map(ConsultationFeeResponse::fromEntity)
                          .toList();

    }
}
