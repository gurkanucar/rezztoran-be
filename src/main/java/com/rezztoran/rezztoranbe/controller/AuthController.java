package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.LoginModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetRequest;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final ModelMapper modelMapper;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Object>> register(
      @Valid @RequestBody RegisterModel registerModel) {
    var registerResponse = modelMapper.map(authService.tryRegister(registerModel), UserDTO.class);
    return ApiResponse.builder().data(registerResponse).build();
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginModel loginModel) {
    var token = authService.tryLogin(loginModel);
    return ApiResponse.builder().data(token).build();
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<Object>> getMyself() {
    var user = modelMapper.map(authService.getAuthenticatedUser(), UserDTO.class);
    return ApiResponse.builder().data(user).build();
  }

  @PostMapping("/reset-request")
  public ResponseEntity<ApiResponse<Object>> resetMail(
      @RequestBody PasswordResetRequest passwordResetRequest) {
    authService.resetPasswordRequestCodeGenerate(passwordResetRequest.getMail());
    return ApiResponse.builder().build();
  }

  @PostMapping("/reset-password")
  public ResponseEntity<ApiResponse<Object>> resetPassword(
      @RequestBody PasswordResetModel passwordResetModel) {
    authService.resetPassword(passwordResetModel);
    return ApiResponse.builder().build();
  }
}
