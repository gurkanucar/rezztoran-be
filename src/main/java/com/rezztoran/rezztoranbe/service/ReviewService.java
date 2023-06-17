package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.dto.request.ReviewRequestModel;
import java.util.List;
import java.util.Map;

/** The interface Review service. */
public interface ReviewService {
  /**
   * Create review review dto.
   *
   * @param request the request
   * @return the review dto
   */
  ReviewDTO createReview(ReviewRequestModel request);

  /**
   * Update review review dto.
   *
   * @param request the request
   * @return the review dto
   */
  ReviewDTO updateReview(ReviewRequestModel request);

  /**
   * Delete review by id.
   *
   * @param id the id
   */
  void deleteReviewById(Long id);

  /**
   * Gets reviews by restaurant.
   *
   * @param id the id
   * @return the reviews by restaurant
   */
  List<ReviewDTO> getReviewsByRestaurant(Long id);

  /**
   * Gets reviews by user.
   *
   * @param id the id
   * @return the reviews by user
   */
  List<ReviewDTO> getReviewsByUser(Long id);

  /**
   * Calculate star count by restaurant double.
   *
   * @param id the id
   * @return the double
   */
  Double calculateStarCountByRestaurant(Long id);

  /**
   * Calculate star count by restaurant map.
   *
   * @param ids the ids
   * @return the map
   */
  Map<Long, Double> calculateStarCountByRestaurant(List<Long> ids);

  /**
   * Create review list.
   *
   * @param reviews the reviews
   */
  void createReviewList(List<ReviewRequestModel> reviews);

  long getCount();
}
