package com.morrisco.net.eCommerceSystem.dtos;

import com.morrisco.net.eCommerceSystem.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Data
public class CartDto {
    private UUID id;
    private List<CartItemDto> items= new ArrayList<>();
    private BigDecimal totalPrice= BigDecimal.ZERO;
}
