package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // List of prescribed medicines
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescribedMedicine> prescribedMedicines;

    // One-to-one association with FollowUp details
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "followup_id")
    private FollowUp followUp;

    // Embedded IP Admission details
    @Embedded
    private IPAdmission ipAdmission;
}
