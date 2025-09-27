package com.morrisco.net.eCommerceSystem.dtos;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class AddItemTOCartRequest {
    @NotNull
    private Long productId;
}
