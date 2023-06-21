package com.rezztoran.rezztoranbe.service.impl;

import com.mysql.cj.log.Log;
import com.rezztoran.rezztoranbe.dto.TokenDTO;
import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.LoginModel;
import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetModel;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.kafka.producer.PasswordResetMailProducer;
import com.rezztoran.rezztoranbe.model.PasswordResetInfo;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.service.AuthService;
import com.rezztoran.rezztoranbe.service.TokenService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.security.auth.Login;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The type Auth service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;
    private final ExceptionUtil exceptionUtil;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetMailProducer passwordResetMailProducer;

    public TokenDTO tryLogin(LoginModel loginRequest) {
        try {
            loginRequest = tryChangeUsername(loginRequest);
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

    public Optional<UserDetails> getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return Optional.of((UserDetails) principal);
        }
        return Optional.empty();
    }

    public Optional<User> getAuthenticatedUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return Optional.ofNullable(userService.findUserByUsername(username));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean checkForPermission(Long id) {
        if (getAuthenticatedUser().isPresent()) {
            return getAuthenticatedUser().get().getId().equals(id);
        }
        return false;
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

        var code = generateRandomCode();
        // 2 minutes for password reset request expiration
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(2);

        PasswordResetInfo passwordResetInfo = new PasswordResetInfo();
        passwordResetInfo.setResetPassword(true);
        passwordResetInfo.setResetPasswordCode(code);
        passwordResetInfo.setResetPasswordExpiration(expirationTime);

        user.setPasswordResetInfo(passwordResetInfo);
        userService.save(user);
        var passwordResetMail =
                PasswordResetMail.builder()
                        .code(code)
                        .mailModel(
                                MailModel.builder()
                                        .subject("Password Reset")
                                        .text("Your code is: " + code.toString())
                                        .to(email)
                                        .build())
                        .username(user.getUsername())
                        .mail(email)
                        .build();
        passwordResetMailProducer.sendPasswordResetMail(passwordResetMail);
        log.info("code: {}", code);
    }

    public void resetPassword(PasswordResetModel passwordResetModel) {
        var user = userService.findUserByMail(passwordResetModel.getMail());
        PasswordResetInfo passwordResetInfo = user.getPasswordResetInfo();

        if (passwordResetInfo == null || !passwordResetInfo.isResetPassword()) {
            throw exceptionUtil.buildException(Ex.PASSWORD_RESET_REQUEST_NOT_FOUND_EXCEPTION);
        }

        if (passwordResetInfo.getResetPasswordExpiration().isBefore(LocalDateTime.now())) {
            throw exceptionUtil.buildException(Ex.PASSWORD_RESET_REQUEST_CODE_EXPIRED_EXCEPTION);
        }

        if (!passwordResetInfo.getResetPasswordCode().equals(passwordResetModel.getCode())) {
            throw exceptionUtil.buildException(Ex.PASSWORD_RESET_REQUEST_CODE_WRONG_EXCEPTION);
        }

        passwordResetInfo.setResetPassword(false);
        user.setPasswordChangeVersion(user.getPasswordChangeVersion() + 1);
        user.setPassword(passwordEncoder.encode(passwordResetModel.getPassword()));
        userService.save(user);
    }

    /**
     * Generate random code integer.
     *
     * @return the integer
     */
    public Integer generateRandomCode() {
        int max = 99999;
        int min = 10000;
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    private LoginModel tryChangeUsername(LoginModel data) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(data.getUsername());
        if (mat.matches()) {
            data.setUsername(userService.findUserByMail((data.getUsername())).getUsername());
        }
        return data;
    }
}
