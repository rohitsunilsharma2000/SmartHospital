package com.hms.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSlotResponse {
    private Long doctorId;
    private String doctorName;
    private String department;
    private List<SlotResponse> slots;
}

