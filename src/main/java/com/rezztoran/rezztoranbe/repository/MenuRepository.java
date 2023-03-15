package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Menu repository.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {}
