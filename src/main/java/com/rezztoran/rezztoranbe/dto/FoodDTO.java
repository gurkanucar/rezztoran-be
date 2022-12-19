package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.model.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDTO {
    private String foodName;
    private String foodImage;

    private List<Category> categories;

    private Category mainCategory;

    private Double price;
    private Double cal;

    private Long menu_id;

    public static FoodDTO toDTO(Food food) {
        return FoodDTO.builder()
                .foodName(food.getFoodName())
                .foodImage(food.getFoodImage())
                .categories(food.getCategories())
                .mainCategory(food.getMainCategory())
                .price(food.getPrice())
                .cal(food.getCal())
                .menu_id(food.getMenu().getId())
                .build();
    }

}
