package com.morrisco.net.eCommerceSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemsDto {
    private OrderProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
