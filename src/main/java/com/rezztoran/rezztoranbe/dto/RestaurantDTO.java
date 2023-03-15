package com.rezztoran.rezztoranbe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rezztoran.rezztoranbe.model.Menu;
import com.rezztoran.rezztoranbe.model.Review;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  Boolean isFavorite;
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
  private Map<String, String> restaurantAttributes;
  private Boolean bookingAvailable;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime openingTime;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime closingTime;
  private Integer intervalMinutes;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private List<LocalDate> busyDates;
}
