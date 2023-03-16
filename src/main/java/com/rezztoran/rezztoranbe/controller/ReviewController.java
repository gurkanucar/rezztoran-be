package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.request.ReviewRequestModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
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

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createReview(@RequestBody ReviewRequestModel request) {
    return ApiResponse.builder().build();
  }

  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateReview(@RequestBody ReviewRequestModel request) {
    return ApiResponse.builder().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteReviewById(@PathVariable Long id) {
    return ApiResponse.builder().build();
  }

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<ApiResponse<Object>> getCommentsByRestaurant(@PathVariable Long id) {
    return ApiResponse.builder().build();
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<ApiResponse<Object>> getCommentsByUser(@PathVariable Long id) {
    return ApiResponse.builder().build();
  }
}
