package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByMail(String mail);

  Optional<User> findUserByUsername(String mail);
}
