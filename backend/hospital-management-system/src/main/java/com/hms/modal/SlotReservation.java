package com.hms.modal;

import com.hms.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "slot_reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status; // To track booking, walk-in, etc.

    // For example, the count of additional slots if applicable.
    private Integer additionalSlots;

    // (You can add associations with Appointment or Doctor if required.)
    // Association with Appointment:
    // Many slot reservations can be associated with one appointment.
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    // Association with Doctor:
    // Many slot reservations can be associated with one doctor.
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient; // Optional for initial slot assignment

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Availability slot;


    @Column(nullable = false)
    private LocalDateTime reservationTime;

    private String notes;


}

