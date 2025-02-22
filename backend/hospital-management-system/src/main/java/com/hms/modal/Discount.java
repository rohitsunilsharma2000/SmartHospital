package com.hms.modal;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double discountAmount;      // Discount amount applied on the bill
    private String authorizedBy;        // Who authorized the discount

    @ElementCollection
    private List<String> categories;    // A list of discount categories (e.g., "Seasonal", "Loyalty")
}
