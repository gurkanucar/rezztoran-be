package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {}
