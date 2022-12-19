package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.TokenDTO;
import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.LoginModel;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterModel registerModel) {
        return ResponseEntity.ok(modelMapper.map(authService.tryRegister(registerModel), UserDTO.class));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginModel loginModel) {
        return ResponseEntity.ok(authService.tryLogin(loginModel));
    }


    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyself() {
        return ResponseEntity.ok(modelMapper.map(authService.getAuthenticatedUser(), UserDTO.class));
    }


}
