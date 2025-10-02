package com.morrisco.net.eCommerceSystem.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "email not null")
    private String email;

    @NotNull(message = "password not null")
    private String password;
}
