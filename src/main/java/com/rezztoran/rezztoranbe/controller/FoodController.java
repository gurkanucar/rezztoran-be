package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.FoodDTO;
import com.rezztoran.rezztoranbe.model.Food;
import com.rezztoran.rezztoranbe.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/{id}")
    public ResponseEntity<FoodDTO> getFoodByID(@PathVariable Long id) {
        return ResponseEntity.ok(FoodDTO.toDTO(foodService.getFoodByID(id)));
    }

    @PostMapping
    public ResponseEntity<FoodDTO> createFood(@RequestBody Food food) {
        return ResponseEntity.ok(FoodDTO.toDTO(foodService.createFood(food)));
    }

    @PutMapping
    public ResponseEntity<FoodDTO> updateFood(@RequestBody Food food) {
        return ResponseEntity.ok(FoodDTO.toDTO(foodService.udateFood(food)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodByID(@PathVariable Long id) {
        foodService.deleteFoodByID(id);
        return ResponseEntity.ok().build();
    }

}
