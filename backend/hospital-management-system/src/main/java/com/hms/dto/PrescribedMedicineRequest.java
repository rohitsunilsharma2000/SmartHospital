package com.hms.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PrescribedMedicineRequest {
    private String medicineType;
    private String medicineBrand;
    private String dosageTime;
    private Boolean isSiOpusSit;
    private LocalDate startDate;
    private Boolean isLifelong;
    private Integer numberOfDays;
    private Integer totalStocksAcrossStores;
    private String dosingPeriodicity;
    private String dosageDurationYMD;
    private String mealTimingInstruction;


    // ✅ Add these missing fields
    private Double unitPrice;  // Price per unit of medicine
    private Integer quantity;  // Number of units prescribed
}
