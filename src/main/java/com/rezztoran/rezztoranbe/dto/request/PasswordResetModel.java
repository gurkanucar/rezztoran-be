package com.rezztoran.rezztoranbe.dto.request;

import lombok.Data;

@Data
public class PasswordResetModel {
    private String mail;
    private String password;
    private Integer code;
}
