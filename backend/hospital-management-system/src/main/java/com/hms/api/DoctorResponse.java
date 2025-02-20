package com.hms.api;

import com.hms.dto.AvailabilityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {

    private Long doctorId;
    private String doctorName;
    private String department;
    private Map<String, List<AvailabilityResponse>> slots;
}
