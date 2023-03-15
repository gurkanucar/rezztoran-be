package com.rezztoran.rezztoranbe.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type Login model.
 */
@Data
public class LoginModel {
  @NotBlank private String username;
  @NotBlank private String password;
}
