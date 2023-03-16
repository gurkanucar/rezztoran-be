package com.rezztoran.rezztoranbe.service.impl;

import static java.util.stream.Collectors.groupingBy;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.dto.request.ReviewRequestModel;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

  private final UserService userService;
  private final RestaurantService restaurantService;
  private final ReviewRepository reviewRepository;

  @Override
  public ReviewDTO createReview(ReviewRequestModel request) {
    var user = userService.findUserByID(request.getUserId());
    var restaurant = restaurantService.getById(request.getRestaurantId());

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

  public Review getReviewById(Long id) {
    return reviewRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("review not found!"));
  }

  @Override
  public void deleteReviewById(Long id) {
    var existing = getReviewById(id);
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
  public Double calculateStarCountByRestaurant(Long id) {
    var restaurantData = reviewRepository.findAllByRestaurant_Id(id);
    return restaurantData.stream().mapToInt(Review::getStar).average().orElse(0);
  }

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
}
