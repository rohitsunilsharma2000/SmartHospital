package com.hms.modal;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "custom_medicines")
public class CustomMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String medicineName;  // ✅ Custom medicine name

    private String dosage;        // ✅ Dosage details (e.g., "1 tablet daily")
    private String instructions;  // ✅ Instructions (e.g., "After meal")

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription; // ✅ Linking to Prescription

}

