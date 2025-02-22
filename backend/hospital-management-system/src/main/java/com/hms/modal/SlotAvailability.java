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

@Table(name = "slot_availability",
        uniqueConstraints = @UniqueConstraint(columnNames = "slotNumber"),
        indexes = @Index(columnList = "slotNumber"))
public class SlotAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true, nullable = false) // Ensure it's marked as unique
    private String slotNumber;
    private String time;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (status IN ('UNAVAILABLE', 'AVAILABLE', 'BOOKED'," +
            " 'ADDITIONAL', 'ARRIVED', 'COMPLETED', 'WALKIN', 'BLOCKED', 'NO_SHOW', 'RESERVED','PENDING'))")
    private SlotStatus status;

    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

}
