package com.hms.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequest {

    @NotBlank(message = "Medicine name is required")
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Dosage form is required")
    private String dosageForm;

    @NotBlank(message = "Strength is required")
    private String strength;

    @NotBlank(message = "Category is required")
    private String category;

    @FutureOrPresent(message = "Expiry date must be in the future or present")
    private LocalDate expiryDate;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private Double price;

    @Min(value = 1, message = "Reorder level must be at least 1")
    private Integer reorderLevel;
}
