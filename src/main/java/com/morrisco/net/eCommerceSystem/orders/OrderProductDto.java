package com.morrisco.net.eCommerceSystem.orders;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
}
