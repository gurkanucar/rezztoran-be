package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
