package com.hms.modal;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "room_tariff_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomTariffDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private BigDecimal roomCharges;
    private BigDecimal dutyDoctorCharges;
    private BigDecimal nursingCharges;
}
