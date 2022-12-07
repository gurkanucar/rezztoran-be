package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByMail(String mail);
    Optional<User> findUserByUsername(String mail);

}
