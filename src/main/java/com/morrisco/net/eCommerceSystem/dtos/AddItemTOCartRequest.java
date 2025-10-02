package com.morrisco.net.eCommerceSystem.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemTOCartRequest {
    @NotNull
    private Long productId;
}
