package com.rezztoran.rezztoranbe.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/** The type Password reset request. */
@Data
public class PasswordResetRequest {
  @NotBlank private String mail;
}
