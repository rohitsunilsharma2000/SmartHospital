package com.hms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlotBookingRequest {
    private String mobile;
    private String hospId;
    private Long doctorId;
    private String slotNumber;  // ✅ Updated from slotId to slotNumber    private String source;
    private String patientType;
    private String visitType;
    private String reVisitHospId;
    private boolean emailNotifications;
    private String source;
}
