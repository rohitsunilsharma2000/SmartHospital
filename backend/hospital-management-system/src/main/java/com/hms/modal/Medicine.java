package com.hms.modal;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicines", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Medicine name is required")
    @Column(nullable = false, unique = true)
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
    @Column(nullable = false)
    private LocalDate expiryDate;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private Double price;

    @Min(value = 1, message = "Reorder level must be at least 1")
    private Integer reorderLevel;
}
