package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.model.Menu;
import com.rezztoran.rezztoranbe.model.Review;
import java.util.List;
import lombok.Data;

@Data
public class RestaurantDTO {
  private Long id;
  private String restaurantName;
  private List<String> restaurantImageList;
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
