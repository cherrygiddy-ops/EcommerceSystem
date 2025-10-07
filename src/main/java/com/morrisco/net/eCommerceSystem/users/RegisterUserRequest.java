package com.morrisco.net.eCommerceSystem.users;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class RegisterUserRequest {
    @NotBlank(message = "name is required")
    @NotNull(message = "name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @NotNull(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    @NotNull(message = "password is required")
    private String password;
}
