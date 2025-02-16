package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "billing_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Type of billing item (e.g., consultation, procedure)
    private String type;

    // Name/description of the item
    private String name;

    // Unit price for the item
    private Double unitPrice;

    // Quantity of the item
    private Integer quantity;

    // Discount applied on the item
    private Double discount;

    // Total amount for this item (after discount, etc.)
    private Double total;

    // Many billing items belong to one bill.
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
