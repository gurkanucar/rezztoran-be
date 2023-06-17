package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.AnalyticsDTO;
import com.rezztoran.rezztoranbe.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final FoodService foodService;
    private final BookService bookService;

    @Override
    public AnalyticsDTO getTotalCounts() {

        return AnalyticsDTO.builder()
                .restaurantCount(restaurantService.getCount())
                .userCount(userService.getCount())
                .reviewCount(reviewService.getCount())
                .foodCount(foodService.getCount())
                .bookingCount(bookService.getCount())
                .build();
    }
}
