package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetModel;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.exception.UserNotFoundException;
import com.rezztoran.rezztoranbe.model.Role;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public User create(User user) {
        if (userRepository.findUserByMail(user.getMail()).isPresent() || userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public User create(User user, Role role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }


    public User create(RegisterModel registerUser) {
        var user = new User();
        user.setMail(registerUser.getMail());
        user.setUsername(registerUser.getUsername());
        user.setSurname(registerUser.getSurname());
        user.setName(registerUser.getName());
        user.setRole(registerUser.getRole() == null ? Role.USER : registerUser.getRole());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        return userRepository.save(user);
    }


    public User update(User user) {
        User existing = findUserByID(user.getId());
        existing.setName(user.getName());
        existing.setUsername(user.getUsername());
        existing.setMail(user.getMail());
        return userRepository.save(user);
    }

    public User findUserByID(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found!"));
    }

    public boolean doesUserExistByID(Long id) {
        return userRepository.findById(id).isPresent();
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("user not found!"));
    }

    public User findUserByMail(String mail) {
        return userRepository.findUserByMail(mail)
                .orElseThrow(() -> new UserNotFoundException("user not found!"));
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public void deleteUser(Long id) {
        if (doesUserExistByID(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("user not found!");
        }
    }

    public void resetPasswordRequestCodeGenerate(String email) {
        var user = findUserByMail(email);
        user.setResetPassword(true);
        var code = generateRandomCode();
        user.setResetPasswordCode(code);
        userRepository.save(user);
        mailService.sendSimpleMessage(MailModel.builder()
                .subject("Password Reset")
                .text("Your code is: " + code.toString())
                .to(email).build());
    }

    public void resetPassword(PasswordResetModel passwordResetModel) {
        var user = findUserByMail(passwordResetModel.getMail());
        if (!user.isResetPassword()) {
            throw new RuntimeException("no password reset request!");
        }
        if (!user.getResetPasswordCode().equals(passwordResetModel.getCode())) {
            throw new RuntimeException("password reset code is wrong!");
        }
        user.setResetPassword(false);
        user.setResetPasswordCode(null);
        user.setPassword(passwordEncoder.encode(passwordResetModel.getPassword()));
        userRepository.save(user);
    }

    protected Integer generateRandomCode() {
        int max = 99999;
        int min = 10000;
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }


}
