package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The type User controller. */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final ModelMapper modelMapper;
  private final UserService userService;

  /**
   * Update user response entity.
   *
   * @param user the user
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateUser(@RequestBody User user) {
    var userResponse = modelMapper.map(userService.update(user), UserDTO.class);
    return ApiResponse.builder().data(userResponse).build();
  }

  /**
   * Gets user by id.
   *
   * @param id the id
   * @return the user by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getUserById(@PathVariable Long id) {
    var user = modelMapper.map(userService.findUserByID(id), UserDTO.class);
    return ApiResponse.builder().data(user).build();
  }

  /**
   * Delete response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
    userService.deleteUser(id);
    return ApiResponse.builder().build();
  }

  /**
   * Gets users.
   *
   * @return the users
   */
  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN', 'RESTAURANT_ADMIN')")
  public ResponseEntity<ApiResponse<Object>> searchUsers(
      @RequestParam(required = false) String searchTerm,
      @RequestParam(defaultValue = "name") String sortField,
      @RequestParam(defaultValue = "ASC") String sortDirection,
      @PageableDefault(size = 20) Pageable pageable) {
    Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
    var result = userService.getUsers(searchTerm, sortField, direction, pageable);
    return ApiResponse.builder().pageableData(result).build();
  }

  /**
   * Create user by role response entity.
   *
   * @param registerModel the register model
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/create-user-by-role")
  public ResponseEntity<ApiResponse<Object>> createUserByRole(
      @RequestBody RegisterModel registerModel) {
    var user = modelMapper.map(userService.create(registerModel), UserDTO.class);
    return ApiResponse.builder().data(user).build();
  }
}
