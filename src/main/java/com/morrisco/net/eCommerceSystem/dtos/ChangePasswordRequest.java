package com.morrisco.net.eCommerceSystem.dtos;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private  String newPassword;
}
