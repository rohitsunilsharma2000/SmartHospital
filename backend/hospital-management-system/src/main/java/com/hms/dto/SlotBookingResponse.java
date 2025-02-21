package com.hms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlotBookingResponse {
    private Long bookingId;
    private String patientName;
    private String doctorName;
    private String slotTime;
    private String visitType;
    private String source;
    private boolean emailNotifications;
}
