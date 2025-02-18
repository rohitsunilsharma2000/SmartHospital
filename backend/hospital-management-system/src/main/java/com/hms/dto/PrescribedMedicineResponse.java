package com.hms.dto;



import com.hms.modal.PrescribedMedicine;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescribedMedicineResponse {
    private String medicineType;
    private String medicineBrand;
    private String dosageTime;
    private Integer numberOfDays;
    private Double unitPrice;
    private Integer quantity;
    private Double total;

    public static PrescribedMedicineResponse fromEntity(PrescribedMedicine medicine) {
        return PrescribedMedicineResponse.builder()
                                         .medicineType(medicine.getMedicineType())
                                         .medicineBrand(medicine.getMedicineBrand())
                                         .dosageTime(medicine.getDosageTime())
                                         .numberOfDays(medicine.getNumberOfDays())
                                         .unitPrice(medicine.getUnitPrice())
                                         .quantity(medicine.getQuantity())
                                         .total(medicine.getTotal())
                                         .build();
    }
}
