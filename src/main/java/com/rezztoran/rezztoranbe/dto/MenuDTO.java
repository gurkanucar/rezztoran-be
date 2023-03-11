package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.model.Food;
import java.util.List;
import lombok.Data;

@Data
public class MenuDTO {
  private RestaurantDTO restaurant;
  private List<Food> foods;
}
