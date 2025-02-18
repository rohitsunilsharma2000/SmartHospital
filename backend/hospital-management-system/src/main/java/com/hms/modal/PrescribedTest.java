package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "prescribed_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescribedTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The test name (from MDM or manually entered)
    private String testName;

    // Quantity of the test ordered
    private Integer quantity;

    // ✅ Add missing fields
    @Column(nullable = false)
    private Double unitPrice; // ✅ Test cost per unit

    @Column(nullable = false)
    private Double total; // ✅ Total cost (calculated as `unitPrice * quantity`)


    // Note or instructions to the lab
    private String noteOrInstructions;

    // Many prescribed tests belong to one EMR.
    @ManyToOne
    @JoinColumn(name = "emr_id")
    private ElectronicMedicalRecord electronicMedicalRecord;

    // Optionally, a test can be associated with a FollowUp visit
    @ManyToOne
    @JoinColumn(name = "followup_id")
    private FollowUp followUp;
}
