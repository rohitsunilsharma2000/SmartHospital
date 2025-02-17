package com.hms.dto;


import com.hms.enums.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private Long slotId;
    private AppointmentType appointmentType;
}
