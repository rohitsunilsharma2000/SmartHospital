package com.hms.service;

import com.hms.dto.DoctorFeeRequest;
import com.hms.dto.DoctorFeeResponse;
import com.hms.modal.DoctorFee;
import com.hms.repository.DoctorFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorFeeService {

    private final DoctorFeeRepository doctorFeeRepository;

    // ✅ Read fees by doctor name or ID (Typeahead)
    // ✅ Read fees by doctor name, ID, or consultationType
    public List<DoctorFeeResponse> getFees(String name, Long id, String consultationType) {
        List<DoctorFee> fees;

        if (id != null) {
            fees = doctorFeeRepository.findByDoctorId(id); // ✅ Search by doctor ID
        } else if (name != null) {
            fees = doctorFeeRepository.findByDoctor_NameContainingIgnoreCase(name); // ✅ Search by doctor name
        } else if (consultationType != null) {
            fees = doctorFeeRepository.findByConsultationTypeContainingIgnoreCase(consultationType); // ✅ Search by consultation type
        } else {
            fees = doctorFeeRepository.findAll(); // ✅ Return all fees if no filters are applied
        }

        return fees.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    // ✅ Update fee
    public DoctorFeeResponse updateFee(Long id, DoctorFeeRequest request) {
        DoctorFee fee = doctorFeeRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Fee not found"));

        fee.setConsultationType(request.getConsultationType());
        fee.setUnitPrice(request.getUnitPrice());

        return mapToResponse(doctorFeeRepository.save(fee));
    }

    // ✅ Delete fee
    public void deleteFee(Long id) {
        doctorFeeRepository.deleteById(id);
    }

    // ✅ Helper method to map entity to response
    private DoctorFeeResponse mapToResponse(DoctorFee fee) {
        DoctorFeeResponse response = new DoctorFeeResponse();
        response.setId(fee.getId());
        response.setConsultationType(fee.getConsultationType());
        response.setUnitPrice(fee.getUnitPrice());
        response.setDoctorId(fee.getDoctor().getId());
        response.setDoctorName(fee.getDoctor().getName());
        return response;
    }
}
