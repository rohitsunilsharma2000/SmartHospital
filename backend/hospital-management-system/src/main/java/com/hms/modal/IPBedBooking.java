package com.hms.modal;

import com.hms.enums.PaymentMode;
import com.hms.enums.RoomCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "ip_bed_booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IPBedBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Association with a patient (assumes a Patient entity exists)
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Room category from a predefined list
    @Enumerated(EnumType.STRING)
    private RoomCategory roomCategory;

    // Association with the consulting doctor (assumes a Doctor entity exists)
    @ManyToOne
    @JoinColumn(name = "consulting_doctor_id")
    private Doctor consultingDoctor;

    // Expected date of delivery (if applicable)
    private LocalDate expectedDateOfDelivery;

    // Advance amount paid
    private Double advanceAmt;

    // Payment mode used for the advance payment
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    // Status (could be an enum as well; here we leave it as a String)
    private String status;
}
