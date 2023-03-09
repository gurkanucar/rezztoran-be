package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.enums.Role;
import com.rezztoran.rezztoranbe.model.User;
import java.util.List;

public interface UserService {

  User create(User user);

  User create(User user, Role role);

  User create(RegisterModel registerUser);

  User save(User user);

  User update(User user);

  User findUserByID(Long id);

  boolean doesUserExistByID(Long id);

  User findUserByUsername(String username);

  User findUserByMail(String mail);

  List<User> getUsers();

  void deleteUser(Long id);
}
