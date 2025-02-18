package com.hms.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescribedTestRequest {
    private String testName;
    private Integer quantity;
    private String noteOrInstructions;

    // ✅ Add missing fields
    private Double unitPrice; // Cost per test
    private Double total; // Total cost = unitPrice * quantity
}
