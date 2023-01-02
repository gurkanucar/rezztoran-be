package com.rezztoran.rezztoranbe.dto.request;

import com.rezztoran.rezztoranbe.enums.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterModel {
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String mail;
    @NotBlank
    private String password;
    private String name;
    private String surname;
    private Role role;
}