package com.rezztoran.rezztoranbe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.model.Food;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Food dto. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodDTO {

  private Long foodId;
  private String foodName;
  private String foodImage;

  private List<Category> categories;

  private Category category;

  private Double price;
  private Double cal;

  private Long restaurantId;
  private String restaurantName;

  /**
   * To dto food dto.
   *
   * @param food the food
   * @return the food dto
   */
  public static FoodDTO toDTO(Food food) {
    return FoodDTO.builder()
        .foodName(food.getFoodName())
        .foodImage(food.getFoodImage())
        .foodId(food.getId())
        .category(food.getCategory())
        .price(food.getPrice())
        .restaurantId(food.getRestaurant() != null ? food.getRestaurant().getId() : null)
        .restaurantName(
            food.getRestaurant() != null ? food.getRestaurant().getRestaurantName() : null)
        .cal(food.getCal())
        .build();
  }
}
