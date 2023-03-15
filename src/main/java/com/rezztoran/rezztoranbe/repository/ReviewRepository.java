package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Review repository. */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {}
