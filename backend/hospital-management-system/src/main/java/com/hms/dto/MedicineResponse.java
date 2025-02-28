package com.hms.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponse {

    private Long id;
    private String name;
    private String brand;
    private String dosageForm;
    private String strength;
    private String category;
    private LocalDate expiryDate;
    private Integer stockQuantity;
    private Double price;
    private Integer reorderLevel;
}
