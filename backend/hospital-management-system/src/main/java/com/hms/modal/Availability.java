package com.hms.modal;

import com.hms.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    @Column(nullable = false, length = 20)
    private String day; // Ensure it's stored as VARCHAR(20)

    @Column(nullable = false)
    private String startTime; // e.g., "07:00"

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String endTime; // e.g., "21:00"

    // For example: "Consultation" or "Video Consultation"
    private String consultationType;

    // Link back to the doctor who owns this availability.
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Ensures database does not allow NULL values
    private SlotStatus status = SlotStatus.AVAILABLE;  // Default value

    @PrePersist
    public void setDefaultDate() {
        if (this.date == null) {
            this.date = LocalDate.now(); // Set current date if not provided
        }
    }
}

