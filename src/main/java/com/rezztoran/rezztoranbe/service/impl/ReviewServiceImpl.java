package com.rezztoran.rezztoranbe.service.impl;

import static java.util.stream.Collectors.groupingBy;

import com.rezztoran.rezztoranbe.aop.AuthorizeCheck;
import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.dto.request.ReviewRequestModel;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Review;
import com.rezztoran.rezztoranbe.repository.ReviewRepository;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.ReviewService;
import com.rezztoran.rezztoranbe.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** The type Review service. */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

  private final UserService userService;
  private final RestaurantService restaurantService;
  private final ReviewRepository reviewRepository;
  private final ExceptionUtil exceptionUtil;
  private final AuthServiceImpl authService;

  private static ReviewDTO getReviewDTO(Review x) {
    return ReviewDTO.builder()
        .id(x.getId())
        .restaurantId(x.getRestaurant().getId())
        .restaurantName(x.getRestaurant().getRestaurantName())
        .userId(x.getId())
        .username(x.getUser().getUsername())
        .content(x.getContent())
        .star(x.getStar())
        .build();
  }

  @Override
  @AuthorizeCheck(
      field = "userId",
      exceptRoles = {"ADMIN", "RESTAURANT_ADMIN"})
  public ReviewDTO createReview(ReviewRequestModel request) {
    var user = userService.findUserByID(request.getUserId());
    var restaurant = restaurantService.getById(request.getRestaurantId());

    if (Boolean.TRUE.equals(
        reviewRepository.existsByUser_IdAndRestaurant_Id(user.getId(), restaurant.getId()))) {
      throw exceptionUtil.buildException(Ex.REVIEW_ALREADY_EXISTS_EXCEPTION);
    }

    var savedRecord =
        reviewRepository.save(
            Review.builder()
                .content(request.getContent())
                .star(request.getStar())
                .user(user)
                .restaurant(restaurant)
                .build());
    return getReviewDTO(savedRecord);
  }

  @Override
  public ReviewDTO updateReview(ReviewRequestModel request) {

    var existing = getReviewById(request.getId());

    // for checking user and restaurant
    userService.findUserByID(request.getUserId());
    restaurantService.getById(request.getRestaurantId());

    existing.setContent(request.getContent());
    existing.setStar(request.getStar());
    return getReviewDTO(reviewRepository.save(existing));
  }

  /**
   * Gets review by id.
   *
   * @param id the id
   * @return the review by id
   */
  public Review getReviewById(Long id) {
    return reviewRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.REVIEW_NOT_FOUND_EXCEPTION));
  }

  @Override
  public void deleteReviewById(Long id) {
    var authUserId = authService.getAuthenticatedUser().get().getId();
    var existing = getReviewById(id);
    if (!authUserId.equals(existing.getUser().getId())) {
      throw exceptionUtil.buildException(Ex.FORBIDDEN_EXCEPTION);
    }
    reviewRepository.delete(existing);
  }

  @Override
  public List<ReviewDTO> getReviewsByRestaurant(Long id) {
    return reviewRepository.findAllByRestaurant_Id(id).stream()
        .map(ReviewServiceImpl::getReviewDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<ReviewDTO> getReviewsByUser(Long id) {
    return reviewRepository.findAllByUser_Id(id).stream()
        .map(ReviewServiceImpl::getReviewDTO)
        .collect(Collectors.toList());
  }

  @Override
  public Double calculateStarCountByRestaurant(Long id) {
    var restaurantData = reviewRepository.findAllByRestaurant_Id(id);
    return restaurantData.stream().mapToInt(Review::getStar).average().orElse(0);
  }

  /**
   * Calculate star count double.
   *
   * @param reviews the reviews
   * @return the double
   */
  public Double calculateStarCount(List<Review> reviews) {
    return reviews.stream().mapToInt(Review::getStar).average().orElse(0);
  }

  @Override
  public Map<Long, Double> calculateStarCountByRestaurant(List<Long> ids) {
    var restaurantData = reviewRepository.findAllByRestaurantIds(ids);

    var resultMap = new HashMap<Long, Double>();

    restaurantData.stream()
        .collect(groupingBy(x -> x.getRestaurant().getId()))
        .forEach((key, value) -> resultMap.put(key, calculateStarCount(value)));

    // for (Entry<Long, List<Review>> entry : groupedRestaurants.entrySet()) {}

    return resultMap;
  }

  @Override
  public void createReviewList(List<ReviewRequestModel> reviews) {
    reviews.forEach(this::createReview);
  }
}
