package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.model.Food;
import lombok.Data;

import java.util.List;
@Data
public class MenuDTO {
    private RestaurantDTO restaurant;
    private List<Food> foods;
}
