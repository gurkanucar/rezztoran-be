package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.RegisterModel;
import com.rezztoran.rezztoranbe.enums.Role;
import com.rezztoran.rezztoranbe.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/** The interface User service. */
public interface UserService {

  /**
   * Create user.
   *
   * @param user the user
   * @return the user
   */
  User create(User user);

  /**
   * Create user.
   *
   * @param user the user
   * @param role the role
   * @return the user
   */
  User create(User user, Role role);

  /**
   * Create user.
   *
   * @param registerUser the register user
   * @return the user
   */
  User create(RegisterModel registerUser);

  /**
   * Save user.
   *
   * @param user the user
   * @return the user
   */
  User save(User user);

  /**
   * Update user.
   *
   * @param user the user
   * @return the user
   */
  User update(User user);

  /**
   * Find user by id user.
   *
   * @param id the id
   * @return the user
   */
  User findUserByID(Long id);

  /**
   * Does user exist by id boolean.
   *
   * @param id the id
   * @return the boolean
   */
  boolean doesUserExistByID(Long id);

  /**
   * Find user by username user.
   *
   * @param username the username
   * @return the user
   */
  User findUserByUsername(String username);

  /**
   * Find user by mail user.
   *
   * @param mail the mail
   * @return the user
   */
  User findUserByMail(String mail);

  /**
   * Gets users.
   *
   * @param searchTerm the search term
   * @param sortField the sort field
   * @param sortDirection the sort direction
   * @param pageable the pageable
   * @return the users
   */
  Page<UserDTO> getUsers(
      String searchTerm, String sortField, Sort.Direction sortDirection, Pageable pageable);

  /**
   * Delete user.
   *
   * @param id the id
   */
  void deleteUser(Long id);

  /**
   * Get total USER count.
   *
   */
  long getCount();
}
