package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.TokenDTO;
import com.rezztoran.rezztoranbe.dto.request.LoginModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetModel;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.model.User;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;

/** The interface Auth service. */
public interface AuthService {

  /**
   * Try login token dto.
   *
   * @param loginRequest the login request
   * @return the token dto
   */
  TokenDTO tryLogin(LoginModel loginRequest);

  /**
   * Gets authenticated user.
   *
   * @return the authenticated user
   */
  Optional<User> getAuthenticatedUser();

  /**
   * Gets authenticated user details.
   *
   * @return the authenticated user details
   */
  Optional<UserDetails> getAuthenticatedUserDetails();

  /**
   * Check for permission boolean.
   *
   * @param id the id
   * @return the boolean
   */
  boolean checkForPermission(Long id);

  /**
   * Try register user.
   *
   * @param registerModel the register model
   * @return the user
   */
  User tryRegister(RegisterModel registerModel);

  /**
   * Reset password request code generate.
   *
   * @param email the email
   */
  void resetPasswordRequestCodeGenerate(String email);

  /**
   * Reset password.
   *
   * @param passwordResetModel the password reset model
   */
  void resetPassword(PasswordResetModel passwordResetModel);
}
