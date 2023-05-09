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

/** The type Auth controller. */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final ModelMapper modelMapper;

  /**
   * Register response entity.
   *
   * @param registerModel the register model
   * @return the response entity
   */
  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Object>> register(
      @Valid @RequestBody RegisterModel registerModel) {
    var registerResponse = modelMapper.map(authService.tryRegister(registerModel), UserDTO.class);
    return ApiResponse.builder().data(registerResponse).build();
  }

  /**
   * Login response entity.
   *
   * @param loginModel the login model
   * @return the response entity
   */
  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody LoginModel loginModel) {
    var token = authService.tryLogin(loginModel);
    return ApiResponse.builder().data(token).build();
  }

  /**
   * Gets myself.
   *
   * @return the myself
   */
  @GetMapping("/me")
  public ResponseEntity<ApiResponse<Object>> getMyself() {
    var user = modelMapper.map(authService.getAuthenticatedUser().get(), UserDTO.class);
    return ApiResponse.builder().data(user).build();
  }

  /**
   * Reset mail response entity.
   *
   * @param passwordResetRequest the password reset request
   * @return the response entity
   */
  @PostMapping("/reset-request")
  public ResponseEntity<ApiResponse<Object>> resetMail(
      @Valid @RequestBody PasswordResetRequest passwordResetRequest) {
    authService.resetPasswordRequestCodeGenerate(passwordResetRequest.getMail());
    return ApiResponse.builder().build();
  }

  /**
   * Reset password response entity.
   *
   * @param passwordResetModel the password reset model
   * @return the response entity
   */
  @PostMapping("/reset-password")
  public ResponseEntity<ApiResponse<Object>> resetPassword(
      @Valid @RequestBody PasswordResetModel passwordResetModel) {
    authService.resetPassword(passwordResetModel);
    return ApiResponse.builder().build();
  }
}
