package com.hms.dto;


import com.hms.enums.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private Long slotId;
    private AppointmentType appointmentType;

    private String mainComplaint;
    private String visitType;
    private LocalDateTime appointmentDateTime;

}
