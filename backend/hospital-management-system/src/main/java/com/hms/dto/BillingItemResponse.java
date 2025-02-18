package com.hms.dto;


import com.hms.modal.BillingItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillingItemResponse {
    private Long id;
    private String type;
    private String name;
    private Double unitPrice;
    private Integer quantity;
    private Double discount;
    private Double total;

    public static BillingItemResponse fromEntity(BillingItem item) {
        return BillingItemResponse.builder()
                                  .id(item.getId())
                                  .type(item.getType())
                                  .name(item.getName())
                                  .unitPrice(item.getUnitPrice())
                                  .quantity(item.getQuantity())
                                  .discount(item.getDiscount())
                                  .total(item.getTotal())
                                  .build();
    }
}
