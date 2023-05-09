package com.rezztoran.rezztoranbe.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Password reset info. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "password_reset_info")
public class PasswordResetInfo extends BaseEntity {

  @Column(name = "reset_password")
  private boolean resetPassword;

  @Column(name = "reset_password_code")
  private Integer resetPasswordCode;

  @Column(name = "reset_password_expiration")
  private LocalDateTime resetPasswordExpiration;

  @OneToOne(mappedBy = "passwordResetInfo")
  private User user;
}
