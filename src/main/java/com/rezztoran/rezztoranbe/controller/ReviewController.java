package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.request.ReviewRequestModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.ReviewService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Review controller. */
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  /**
   * Create review response entity.
   *
   * @param request the request
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createReview(
      @Valid @RequestBody ReviewRequestModel request) {
    return ApiResponse.builder().data(reviewService.createReview(request)).build();
  }

  /**
   * Create review response entity.
   *
   * @param reviews the reviews
   * @return the response entity
   */
  @PostMapping("/insert-list")
  public ResponseEntity<ApiResponse<Object>> createReview(
      @Valid @RequestBody List<ReviewRequestModel> reviews) {
    reviewService.createReviewList(reviews);
    return ApiResponse.builder().build();
  }

  /**
   * Update review response entity.
   *
   * @param request the request
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateReview(
      @Valid @RequestBody ReviewRequestModel request) {
    return ApiResponse.builder().data(reviewService.updateReview(request)).build();
  }

  /**
   * Delete review by id response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteReviewById(@PathVariable Long id) {
    reviewService.deleteReviewById(id);
    return ApiResponse.builder().build();
  }

  /**
   * Gets reviews by restaurant.
   *
   * @param id the id
   * @return the reviews by restaurant
   */
  @GetMapping("/restaurant/{id}")
  public ResponseEntity<ApiResponse<Object>> getReviewsByRestaurant(@PathVariable Long id) {
    return ApiResponse.builder().data(reviewService.getReviewsByRestaurant(id)).build();
  }

  /**
   * Gets reviews by user.
   *
   * @param id the id
   * @return the reviews by user
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<ApiResponse<Object>> getReviewsByUser(@PathVariable Long id) {
    return ApiResponse.builder().data(reviewService.getReviewsByUser(id)).build();
  }
}
