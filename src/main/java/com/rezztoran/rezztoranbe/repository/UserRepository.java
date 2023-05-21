package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface User repository. */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Find user by mail optional.
   *
   * @param mail the mail
   * @return the optional
   */
  Optional<User> findUserByMail(String mail);

  /**
   * Find user by username optional.
   *
   * @param mail the mail
   * @return the optional
   */
  Optional<User> findUserByUsername(String mail);

  Page<User> findAll(Specification<User> spec, Pageable pageable);
}
