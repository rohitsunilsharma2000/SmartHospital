package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "patient_mobile", referencedColumnName = "mobile"),
            @JoinColumn(name = "patient_hosp_id", referencedColumnName = "hospId")
    })
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = true)
    private PrescriptionTemplate template;

    @ManyToMany
    @JoinTable(name = "prescription_medicines",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id"))
    private List<Medicine> medicines;

    @ElementCollection
    private List<String> customMedicines;

    @ManyToMany
    @JoinTable(name = "prescription_lab_tests",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "lab_test_id"))
    private List<MedicalLabTest> labTests;

    @ElementCollection
    private List<String> customTests;

    private String prescriptionDate;

    // ✅ NEW FIELD: Link Prescription to a SlotBooking
    @OneToOne
    @JoinColumn(name = "slot_booking_id", unique = true)
    private SlotBooking slotBooking;
}

