package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAll() {
        return ResponseEntity.ok(restaurantService.getRestaurants()
                .stream().map(x -> mapper.map(x, RestaurantDTO.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getById(@PathVariable Long id) {
        var response = mapper.map(restaurantService.getById(id), RestaurantDTO.class);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<RestaurantDTO> create(@RequestBody Restaurant restaurant) {
        var response = mapper.map(restaurantService.create(restaurant), RestaurantDTO.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert-list")
    public ResponseEntity<List<RestaurantDTO>> create(@RequestBody List<Restaurant> restaurants) {
        return ResponseEntity.ok(restaurantService.create(restaurants)
                .stream().map(x -> mapper.map(x, RestaurantDTO.class))
                .collect(Collectors.toList()));
    }


    @PutMapping
    public ResponseEntity<RestaurantDTO> update(@RequestBody Restaurant restaurant) {
        var response = mapper.map(restaurantService.update(restaurant), RestaurantDTO.class);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update-owner")
    public ResponseEntity<RestaurantDTO> updateOwner(@RequestBody Restaurant restaurant) {
        var response = mapper.map(restaurantService.updateOwner(restaurant), RestaurantDTO.class);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.ok().build();
    }

}
