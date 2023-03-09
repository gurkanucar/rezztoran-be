package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.TokenDTO;
import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.LoginModel;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.exception.WrongCredientalsException;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.service.AuthService;
import com.rezztoran.rezztoranbe.service.TokenService;
import com.rezztoran.rezztoranbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final TokenService tokenService;
  private final ModelMapper modelMapper;

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
    } catch (final BadCredentialsException badCredentialsException) {
      throw new WrongCredientalsException("Invalid Username or Password");
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
}
