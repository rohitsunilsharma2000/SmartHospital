package com.hms.modal;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalLabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;
    private String category; // Example: Pathology, Radiology, etc.
    private String description;
    private BigDecimal cost;
    private Boolean fastingRequired;
}
