package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.StatisticDto;
import com.rezztoran.rezztoranbe.service.BookService;
import com.rezztoran.rezztoranbe.service.FoodService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.ReviewService;
import com.rezztoran.rezztoranbe.service.StatisticsService;
import com.rezztoran.rezztoranbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

  private final UserService userService;
  private final RestaurantService restaurantService;
  private final ReviewService reviewService;
  private final FoodService foodService;
  private final BookService bookService;

  @Override
  public StatisticDto getTotalCounts() {
    return StatisticDto.builder()
        .restaurantCount(restaurantService.getCount())
        .userCount(userService.getCount())
        .reviewCount(reviewService.getCount())
        .foodCount(foodService.getCount())
        .bookingCount(bookService.getCount())
        .build();
  }
}
