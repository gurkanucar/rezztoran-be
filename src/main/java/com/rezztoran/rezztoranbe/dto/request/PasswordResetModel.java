package com.rezztoran.rezztoranbe.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type Password reset model.
 */
@Data
public class PasswordResetModel {
  @NotBlank private String mail;
  @NotBlank private String password;
  @NotBlank private Integer code;
}
