package com.rezztoran.rezztoranbe.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequest {
  @NotBlank private String mail;
}
