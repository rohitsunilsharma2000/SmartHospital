package com.hms.dto;


import com.hms.modal.Appointment;
import com.hms.modal.ConsultationFee;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationFeeResponse {

    private String itemType;
    private String itemName;
    private Double unitPrice;
    private Integer quantity;
    private Double discount;
    private Double total;

    public static ConsultationFeeResponse fromEntity( ConsultationFee fee) {
        return ConsultationFeeResponse.builder()
                                      .itemType("Consultation")
                                      .itemName(fee.getDoctor().getName() + " - " + fee.getConsultationType())
                                      .unitPrice(fee.getUnitPrice())
                                      .quantity(1)
                                      .discount(0.0)
                                      .total(fee.getUnitPrice())
                                      .build();
    }
}
