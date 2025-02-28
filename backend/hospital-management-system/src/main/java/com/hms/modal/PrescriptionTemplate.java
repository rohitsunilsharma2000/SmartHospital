package com.hms.modal;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prescription_templates")
public class PrescriptionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String templateName;
    private String description;

    // ✅ Link to predefined medicines from the Medicine table
    @ManyToMany
    @JoinTable(
            name = "template_medicines",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<Medicine> medicines;

    // ✅ Store custom medicine names
    @ElementCollection
    private List<String> customMedicines;

    // ✅ Link to predefined medical lab tests from the MedicalLabTest table
    @ManyToMany
    @JoinTable(
            name = "template_lab_tests",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "lab_test_id")
    )
    private List<MedicalLabTest> predefinedMedicalLabTests;

    // ✅ Store custom lab test names
    @ElementCollection
    private List<String> customTests;
}
