package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.TokenDTO;
import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.LoginModel;
import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetModel;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.service.AuthService;
import com.rezztoran.rezztoranbe.service.MailService;
import com.rezztoran.rezztoranbe.service.TokenService;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserServiceImpl userService;
  private final TokenService tokenService;
  private final ModelMapper modelMapper;
  private final ExceptionUtil exceptionUtil;
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;

  public TokenDTO tryLogin(LoginModel loginRequest) {
    try {
      Authentication auth =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.getUsername(), loginRequest.getPassword()));
      return TokenDTO.builder()
          .accessToken(tokenService.generateToken(auth))
          .user(
              modelMapper.map(
                  userService.findUserByUsername(loginRequest.getUsername()), UserDTO.class))
          .build();
    } catch (Exception e) {
      throw exceptionUtil.buildException(Ex.WRONG_CREDENTIALS_EXCEPTION);
    }
  }

  public User getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userService.findUserByUsername(username);
  }

  public boolean checkForPermission(Long id) {
    return getAuthenticatedUser().getId().equals(id);
  }

  public User tryRegister(RegisterModel registerModel) {
    return userService.create(
        User.builder()
            .username(registerModel.getUsername())
            .mail(registerModel.getMail())
            .name(registerModel.getName())
            .surname(registerModel.getSurname())
            .password(registerModel.getPassword())
            .build());
  }

  public void resetPasswordRequestCodeGenerate(String email) {
    var user = userService.findUserByMail(email);
    user.setResetPassword(true);
    var code = generateRandomCode();
    user.setResetPasswordCode(code);
    userService.save(user);
    mailService.sendSimpleMessage(
        MailModel.builder()
            .subject("Password Reset")
            .text("Your code is: " + code.toString())
            .to(email)
            .build());
  }

  public void resetPassword(PasswordResetModel passwordResetModel) {
    var user = userService.findUserByMail(passwordResetModel.getMail());
    if (!user.isResetPassword()) {
      throw new RuntimeException("no password reset request!");
    }
    if (!user.getResetPasswordCode().equals(passwordResetModel.getCode())) {
      throw new RuntimeException("password reset code is wrong!");
    }
    user.setResetPassword(false);
    user.setResetPasswordCode(null);
    user.setPassword(passwordEncoder.encode(passwordResetModel.getPassword()));
    userService.save(user);
  }

  public Integer generateRandomCode() {
    int max = 99999;
    int min = 10000;
    Random r = new Random();
    return r.nextInt(max - min) + min;
  }
}
