package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {

    // Discount amount applied on the bill
    private Double discountAmountAppliedOnTheBill;

    // Who authorized the discount
    private String authorizedBy;

    // A list of discount categories that might apply (e.g., "Seasonal", "Loyalty")
    @ElementCollection
    @CollectionTable(name = "bill_discount_categories", joinColumns = @JoinColumn(name = "bill_id"))
    @Column(name = "discount_category")
    private List<String> discountCategories;
}
