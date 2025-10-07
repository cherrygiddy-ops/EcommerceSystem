package com.morrisco.net.eCommerceSystem.payments;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckOutRequest {
    @NotNull(message = "cart is required")
    private UUID cartId;
}
