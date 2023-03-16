package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.dto.request.ReviewRequestModel;
import com.rezztoran.rezztoranbe.model.Review;
import java.util.List;
import java.util.Map;

public interface ReviewService {
  ReviewDTO createReview(ReviewRequestModel request);

  ReviewDTO updateReview(ReviewRequestModel request);

  void deleteReviewById(Long id);

  List<ReviewDTO> getReviewsByRestaurant(Long id);

  List<ReviewDTO> getReviewsByUser(Long id);

  Double calculateStarCountByRestaurant(Long id);

  Map<Long, Double> calculateStarCountByRestaurant(List<Long> ids);
}
