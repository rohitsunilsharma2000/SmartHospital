package com.hms.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hms.enums.AppointmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The date and time of the appointment.
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    // Link to the consulting doctor.
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    // Link to the patient (using cascade to save patient details together, if desired)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Main complaint or reason for the appointment.
    private String mainComplaint;

    // E.g., "First-Time Visit" or "Re-Visit"
    private String visitType;


    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Availability slot; // Linking booked slot

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;  // OPD or IPD



    @Column(nullable = false)
    private String status; // Scheduled, Completed, Cancelled

    // ✅ Constructor with ID only
    public Appointment(Long id) {
        this.id = id;
    }

}
