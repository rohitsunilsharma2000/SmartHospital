package com.hms.modal;

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
    private LocalDateTime appointmentDateTime;

    // Link to the consulting doctor.
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    // Link to the patient (using cascade to save patient details together, if desired)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Main complaint or reason for the appointment.
    private String mainComplaint;

    // E.g., "First-Time Visit" or "Re-Visit"
    private String visitType;


}
