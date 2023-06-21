package com.rezztoran.rezztoranbe.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Password reset mail. */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetMail {
  private MailModel mailModel;
  private String username;
  @NotBlank private String mail;
  @NotNull private Integer code;
}
