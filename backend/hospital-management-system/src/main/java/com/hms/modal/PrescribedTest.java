package com.hms.modal;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescribed_tests")  // ✅ Explicitly specify table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescribedTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;
    private Integer quantity;
    private String noteOrInstructions;

    @ManyToOne
    @JoinColumn(name = "emr_id", nullable = false) // ✅ Foreign Key Reference
    private ElectronicMedicalRecord electronicMedicalRecord;

    // If needed, you can also include FollowUp mapping here.
}
