package com.hms.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private String patientType;
    private String visitType;
    private String reVisitHospId;
    private boolean emailNotifications;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private SlotAvailability slot;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "patient_mobile", referencedColumnName = "mobile"),
            @JoinColumn(name = "patient_hosp_id", referencedColumnName = "hospId")
    })
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // ✅ NEW FIELD: Link SlotBooking to a Prescription
    @OneToOne(mappedBy = "slotBooking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Prescription prescription;
}
