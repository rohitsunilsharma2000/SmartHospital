package com.hms.modal;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IPAdmission {
    // Use a string (or an enum) to capture "yes", "no", or "na"
    private String isAdmissionAdvised;
    private String admissionReason;      // e.g., "Surgical", "Medical", "Procedure", etc.
    private Integer tentativeStayInDays; // Tentative duration in days
    private String notes;
}
