package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private Long orderItemId;
    private Long productId;
    private Integer quantity;
    private Double discount;
    private Double orderedProductPrice;

    public static OrderItemDto from(OrderItem item) {
        if (item == null) {
            return null;
        }
        return OrderItemDto.builder()
                .orderItemId(item.getOrderItemId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .discount(item.getDiscount())
                .orderedProductPrice(item.getOrderedProductPrice())
                .build();
    }
}


