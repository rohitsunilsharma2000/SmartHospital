package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "availabilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // These fields could be stored as strings (e.g., "10:00 AM") or using java.time classes.
    private String startTime;  // e.g., "10:00 AM"
    private String endTime;    // e.g., "01:20 PM"

    // For example: "Consultation" or "Video Consultation"
    private String consultationType;

    // Link back to the doctor who owns this availability.
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
