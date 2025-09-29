package com.morrisco.net.eCommerceSystem.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull(message = "quantity must be provided")
    @Min(value = 1, message = "quantity Must Be Greater Than Zero")
    @Max(value = 100, message = "quantity Must Be less Than 100")
    private Integer quantity;
}
