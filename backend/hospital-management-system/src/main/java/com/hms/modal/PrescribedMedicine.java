package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "prescribed_medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescribedMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Medicine details
    private String medicineType;            // e.g., tablet, capsule, etc.
    private String medicineBrand;           // from MDM or typed in
    private String dosageTime;              // e.g., "Morning, Afternoon, Noon"
    private Boolean isSiOpusSit;            // true/false flag as per requirement
    private LocalDate startDate;
    private Boolean isLifelong;
    private Integer numberOfDays;
    private Integer totalStocksAcrossStores;
    private String dosingPeriodicity;
    private String dosageDurationYMD;       // e.g., "1Y 2M 3D"
    private String mealTimingInstruction;   // e.g., "After food, After breakfast, etc."

    // Many medicines belong to one Prescription
    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
