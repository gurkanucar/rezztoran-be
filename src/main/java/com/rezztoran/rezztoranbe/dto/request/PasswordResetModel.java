package com.rezztoran.rezztoranbe.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetModel {
    @NotBlank
    private String mail;
    @NotBlank
    private String password;
    @NotBlank
    private Integer code;
}
