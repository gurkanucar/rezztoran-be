package com.rezztoran.rezztoranbe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rezztoran.rezztoranbe.model.Review;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Restaurant dto. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean isFavorite;

  private Long id;
  private String restaurantName;
  private List<String> restaurantImageList;
  private String restaurantImage;
  private String city;
  private String district;
  private String detailedAddress;
  private Double latitude;
  private Double longitude;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Double starCount;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private UserDTO user;

  private String phone;

  @JsonIgnore private List<Review> reviews;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, String> restaurantAttributes;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean bookingAvailable;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime openingTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime closingTime;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer intervalMinutes;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private List<LocalDate> busyDates;
}
