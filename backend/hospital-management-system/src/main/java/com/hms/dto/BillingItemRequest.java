package com.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingItemRequest {
    private String type;      // Consultation, Diagnostic, Procedure, etc.
    private String name;      // Name of the service/item
    private Double unitPrice; // Price per unit
    private Integer quantity; // Quantity of the item
    private Double discount;  // Discount on the item
    private Double total;     // Total after discount
}
