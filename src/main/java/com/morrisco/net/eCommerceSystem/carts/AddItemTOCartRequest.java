package com.morrisco.net.eCommerceSystem.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemTOCartRequest {
    @NotNull
    private Long productId;
}
