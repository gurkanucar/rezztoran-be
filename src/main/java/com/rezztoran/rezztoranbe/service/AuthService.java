package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.TokenDTO;
import com.rezztoran.rezztoranbe.dto.request.LoginModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetModel;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.model.User;

public interface AuthService {

  TokenDTO tryLogin(LoginModel loginRequest);

  User getAuthenticatedUser();

  boolean checkForPermission(Long id);

  User tryRegister(RegisterModel registerModel);

  void resetPasswordRequestCodeGenerate(String email);

  void resetPassword(PasswordResetModel passwordResetModel);
}
