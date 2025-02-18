package com.hms.dto;

import com.hms.modal.PrescribedTest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescribedTestResponse {
    private String testName;
    private Integer quantity;
    private Double unitPrice;
    private Double total;

    public static PrescribedTestResponse fromEntity(PrescribedTest test) {
        return PrescribedTestResponse.builder()
                                     .testName(test.getTestName())
                                     .quantity(test.getQuantity())
                                     .unitPrice(test.getUnitPrice())
                                     .total(test.getTotal())
                                     .build();
    }
}
