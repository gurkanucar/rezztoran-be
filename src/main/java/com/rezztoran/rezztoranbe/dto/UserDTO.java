package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String mail;
    private String profileImageUrl;
    private boolean resetPassword;
    private Role role;
}
