package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescribedMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Medicine details
    private String medicineType;            // e.g., tablet, capsule, syrup, injection
    private String medicineBrand;           // e.g., "Paracetamol", "Ibuprofen"
    private String dosageTime;              // e.g., "Morning, Afternoon, Night"
    private Boolean isSiOpusSit;            // Indicates if medicine is to be taken only if needed
    private LocalDate startDate;            // Date when medication starts
    private Boolean isLifelong;             // If the medicine is a lifelong prescription
    private Integer numberOfDays;           // Duration in days for which the medicine should be taken
    private Integer totalStocksAcrossStores; // Stock of medicine available across stores
    private String dosingPeriodicity;       // e.g., "Every 8 hours", "Twice a day"
    private String dosageDurationYMD;       // e.g., "1Y 2M 3D" (Years, Months, Days)
    private String mealTimingInstruction;   // e.g., "After food", "Before food"

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;
}

