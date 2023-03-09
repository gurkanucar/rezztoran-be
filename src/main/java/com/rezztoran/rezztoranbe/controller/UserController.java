package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetRequest;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.UserService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final ModelMapper modelMapper;
  private final UserService userService;

  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateUser(@RequestBody User user) {
    var userResponse = modelMapper.map(userService.update(user), UserDTO.class);
    return ApiResponse.builder().data(userResponse).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getUserById(@PathVariable Long id) {
    var user = modelMapper.map(userService.findUserByID(id), UserDTO.class);
    return ApiResponse.builder().data(user).build();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
    userService.deleteUser(id);
    return ApiResponse.builder().build();
  }

  @PostMapping("/reset-request")
  public ResponseEntity<ApiResponse<Object>> resetMail(
      @RequestBody PasswordResetRequest passwordResetRequest) {
    userService.resetPasswordRequestCodeGenerate(passwordResetRequest.getMail());
    return ApiResponse.builder().build();
  }

  @PostMapping("/reset-password")
  public ResponseEntity<ApiResponse<Object>> resetPassword(
      @RequestBody PasswordResetModel passwordResetModel) {
    userService.resetPassword(passwordResetModel);
    return ApiResponse.builder().build();
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getUsers() {
    var users =
        userService.getUsers().stream()
            .map(x -> modelMapper.map(x, UserDTO.class))
            .collect(Collectors.toList());
    return ApiResponse.builder().data(users).build();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/create-user-by-role")
  public ResponseEntity<ApiResponse<Object>> createUserByRole(
      @RequestBody RegisterModel registerModel) {
    var user = modelMapper.map(userService.create(registerModel), UserDTO.class);
    return ApiResponse.builder().data(user).build();
  }
}
