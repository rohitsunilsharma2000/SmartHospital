package com.hms.dto;

import com.hms.modal.Appointment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long id;
    private String doctorName;
    private String patientName;
    private String slotTime;
    private LocalDateTime appointmentDateTime;
    private String appointmentType;
    private String status;

    public static AppointmentResponse fromEntity( Appointment appointment) {
        return AppointmentResponse.builder()
                                  .id(appointment.getId())
                                  .doctorName(appointment.getDoctor().getName())
                                  .patientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName())
                                  .slotTime(appointment.getSlot().getStartTime() + " - " + appointment.getSlot().getEndTime())
                                  .appointmentDateTime(appointment.getAppointmentDateTime())
                                  .appointmentType(appointment.getAppointmentType().toString())
                                  .status(appointment.getStatus())
                                  .build();
    }
}
