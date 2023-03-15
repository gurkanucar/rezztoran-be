package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type User dto. */
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
