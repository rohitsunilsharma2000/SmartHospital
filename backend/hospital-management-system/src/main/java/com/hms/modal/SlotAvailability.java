package com.hms.modal;

import com.hms.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true, nullable = false) // Ensure it's marked as unique
    private String slotNumber;
    private String time;

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

}
