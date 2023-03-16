package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.request.ReviewRequestModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.ReviewService;
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

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createReview(@Valid @RequestBody ReviewRequestModel request) {
    return ApiResponse.builder().data(reviewService.createReview(request)).build();
  }

  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateReview(@Valid @RequestBody ReviewRequestModel request) {
    return ApiResponse.builder().data(reviewService.updateReview(request)).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteReviewById(@PathVariable Long id) {
    reviewService.deleteReviewById(id);
    return ApiResponse.builder().build();
  }

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<ApiResponse<Object>> getReviewsByRestaurant(@PathVariable Long id) {
    return ApiResponse.builder().data(reviewService.getReviewsByRestaurant(id)).build();
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<ApiResponse<Object>> getReviewsByUser(@PathVariable Long id) {
    return ApiResponse.builder().data(reviewService.getReviewsByUser(id)).build();
  }
}
