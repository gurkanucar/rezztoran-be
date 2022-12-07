package com.rezztoran.rezztoranbe.dto.request;

import com.rezztoran.rezztoranbe.model.Role;
import lombok.Data;

@Data
public class RegisterModel {
    private String username;
    private String mail;
    private String password;
    private String name;
    private String surname;
    private Role role;
}