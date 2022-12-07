package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetRequest;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(modelMapper.map(userService.update(user), UserDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        var user = modelMapper.map(userService.findUserByID(id), UserDTO.class);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-request")
    public ResponseEntity<Void> resetMail(@RequestBody PasswordResetRequest passwordResetRequest) {
        userService.resetPasswordRequestCodeGenerate(passwordResetRequest.getMail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
        userService.resetPassword(passwordResetModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers().stream()
                .map(x -> modelMapper.map(x, UserDTO.class))
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-user-by-role")
    public ResponseEntity<UserDTO> createUserByRole(@RequestBody RegisterModel registerModel) {
        return ResponseEntity.ok(modelMapper.map(userService.create(registerModel), UserDTO.class));
    }

}
