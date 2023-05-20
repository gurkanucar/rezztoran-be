package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.enums.Role;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.PasswordResetInfo;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.repository.UserRepository;
import com.rezztoran.rezztoranbe.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** The type User service. */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ExceptionUtil exceptionUtil;

  private static void setPasswordResetInfo(User user) {
    PasswordResetInfo passwordResetInfo = new PasswordResetInfo();
    passwordResetInfo.setResetPassword(false);
    passwordResetInfo.setResetPasswordCode(null);
    passwordResetInfo.setResetPasswordExpiration(null);

    user.setPasswordResetInfo(passwordResetInfo);
  }

  public User create(User user) {
    if (userRepository.findUserByMail(user.getMail()).isPresent()
        || userRepository.findUserByUsername(user.getUsername()).isPresent()) {
      throw exceptionUtil.buildException(Ex.USER_ALREADY_EXISTS_EXCEPTION);
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.USER);

    setPasswordResetInfo(user);

    return userRepository.save(user);
  }

  public User create(User user, Role role) {

    if (userRepository.findUserByMail(user.getMail()).isPresent()
        || userRepository.findUserByUsername(user.getUsername()).isPresent()) {
      throw exceptionUtil.buildException(Ex.USER_ALREADY_EXISTS_EXCEPTION);
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(role);

    setPasswordResetInfo(user);

    return userRepository.save(user);
  }

  public User create(RegisterModel registerUser) {

    if (userRepository.findUserByMail(registerUser.getMail()).isPresent()
        || userRepository.findUserByUsername(registerUser.getUsername()).isPresent()) {
      throw exceptionUtil.buildException(Ex.USER_ALREADY_EXISTS_EXCEPTION);
    }

    var user = new User();
    user.setMail(registerUser.getMail());
    user.setUsername(registerUser.getUsername());
    user.setSurname(registerUser.getSurname());
    user.setName(registerUser.getName());
    user.setRole(registerUser.getRole() == null ? Role.USER : registerUser.getRole());
    user.setPassword(passwordEncoder.encode(registerUser.getPassword()));

    setPasswordResetInfo(user);

    return userRepository.save(user);
  }

  public User save(User user) {
    if (!doesUserExistByID(user.getId())) {
      throw exceptionUtil.buildException(Ex.USER_NOT_FOUND_EXCEPTION);
    }
    return userRepository.save(user);
  }

  public User update(User user) {
    User existing = findUserByID(user.getId());
    existing.setName(user.getName());
    existing.setSurname(user.getSurname());
    existing.setUsername(user.getUsername());
    existing.setMail(user.getMail());
    return userRepository.save(existing);
  }

  public User findUserByID(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.USER_NOT_FOUND_EXCEPTION));
  }

  public boolean doesUserExistByID(Long id) {
    return userRepository.findById(id).isPresent();
  }

  public User findUserByUsername(String username) {
    var user = userRepository.findUserByUsername(username);
    if (user.isPresent()) {
      return user.get();
    }
    throw exceptionUtil.buildException(Ex.USER_NOT_FOUND_EXCEPTION);
  }

  public User findUserByMail(String mail) {
    return userRepository
        .findUserByMail(mail)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.USER_NOT_FOUND_EXCEPTION));
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public void deleteUser(Long id) {
    if (doesUserExistByID(id)) {
      userRepository.deleteById(id);
    } else {
      throw exceptionUtil.buildException(Ex.USER_NOT_FOUND_EXCEPTION);
    }
  }
}
