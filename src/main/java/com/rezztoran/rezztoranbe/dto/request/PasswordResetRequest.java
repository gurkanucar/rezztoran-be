package com.rezztoran.rezztoranbe.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetRequest {
    @NotBlank
    private String mail;
}
