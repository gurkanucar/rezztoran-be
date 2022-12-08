package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.model.Menu;
import com.rezztoran.rezztoranbe.model.Review;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantDTO {
    private String restaurantName;
    private String restaurantImage;
    private String city;
    private String district;
    private String detailedAddress;
    private Double latitude;
    private Double longitude;
    private Double starCount;
    private UserDTO user;
    private String phone;
    private Menu menu;
    private List<Review> reviews;
}
