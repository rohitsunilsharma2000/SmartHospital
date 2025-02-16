package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference the doctor for the appointment (assumes Doctor is a separate entity)
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    // The date and time of the appointment
    private LocalDateTime appointmentDateTime;

    // Patient name (you might also choose to add an association to a Patient entity)
    private String patientName;

    // Who referred the patient
    private String referredBy;

    // List of billing items associated with this bill.
    // Cascade all changes and remove any orphan BillingItems when the Bill is removed.
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillingItem> billingItems;

    // Payment information embedded as part of the Bill.
    private Payment payment;

    // Optional insurance information (if enabled)
    @Embedded
    private InsuranceOption insuranceOption;

    // Discount details for the bill.
    @Embedded
    private Discount discount;
}
