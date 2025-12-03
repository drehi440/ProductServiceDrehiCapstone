package com.rehi.productservicedrehicapstone.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    /**
     * Logical user placing the order.
     */
    private Long userId;

    /**
     * Optional explicit cartId; if null, the user's cart will be used.
     */
    private Long cartId;

    private String paymentMethod;

    private Long addressId;
}


