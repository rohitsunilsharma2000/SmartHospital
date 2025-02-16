package com.hms.modal;

import com.hms.enums.BedType;
import com.hms.modal.Admission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "bed_management")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BedManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Type of bed available
    @Enumerated(EnumType.STRING)
    private BedType bedType;

    // Bed number (e.g., GF2, GMA, GM4, GM5)
    private String bedNumber;

    // Check-in and check-out dates for the bed booking
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    // Current room details (if needed)
    private String currentRoom;

    // Association with an admission (many bed management entries can belong to one admission)
    @ManyToOne
    @JoinColumn(name = "admission_id")
    private Admission admission;

    // Optionally, associate with room tariff details (see next section)
    @ManyToOne
    @JoinColumn(name = "room_tariff_details_id")
    private RoomTariffDetails roomTariffDetails;
}
