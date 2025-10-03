package com.morrisco.net.eCommerceSystem.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckOutRequest {
    @NotNull(message = "cart is required")
    private UUID cartId;
}
