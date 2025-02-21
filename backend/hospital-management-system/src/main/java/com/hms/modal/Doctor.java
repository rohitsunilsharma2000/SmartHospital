package com.hms.modal;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialty;

    // Unique constraint ensures the database rejects duplicate entries.
    @Column(unique = true)
    private String docLicence;

    // Lazy loading: The slotAvailabilities collection will not be loaded until accessed.
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SlotAvailability> slotAvailabilities;



}
