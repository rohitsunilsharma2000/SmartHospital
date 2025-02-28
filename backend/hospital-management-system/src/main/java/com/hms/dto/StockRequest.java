package com.hms.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {
    private Long medicineId;
    private Integer quantity;
}


