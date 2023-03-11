package com.rezztoran.rezztoranbe.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginModel {
  @NotBlank private String username;
  @NotBlank private String password;
}
