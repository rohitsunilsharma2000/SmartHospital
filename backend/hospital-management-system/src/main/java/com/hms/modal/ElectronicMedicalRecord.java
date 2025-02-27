package com.hms.modal;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "electronic_medical_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicMedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associated with a SlotBooking (composite key lookup)
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private SlotBooking slotBooking;


    // One-to-one prescription
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    // ✅ Added One-to-Many relationship with `PrescribedTest`
    @OneToMany(mappedBy = "electronicMedicalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescribedTest> prescribedTests;
}
