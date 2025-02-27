package com.hms.dto;


import com.hms.modal.PrescribedTest;
import com.hms.modal.Prescription;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElectronicMedicalRecordRequest {

    private Long id; // Optional (only required for updates)

    @NotNull(message = "Booking ID is required")
    private Long bookingId; // Required to link with SlotBooking

    private Prescription prescription; // The associated prescription (optional)
    private List<PrescribedTest> prescribedTests;

}
