package com.rezztoran.rezztoranbe.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/** The type Password reset model. */
@Data
public class PasswordResetModel {
  @NotBlank private String mail;
  @NotBlank private String password;
  @NotNull private Integer code;
}
