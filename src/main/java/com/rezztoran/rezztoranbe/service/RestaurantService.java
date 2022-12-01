package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;


    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }


    public Restaurant create(Restaurant restaurant) {
        if (doesRestaurantExistByName(restaurant)) {
            throw new RuntimeException("Restaurant already exists!");
        }
        return restaurantRepository.save(restaurant);
    }


    public Restaurant update(Restaurant restaurant) {
        var existing = getById(restaurant.getId());
        existing.setCity(restaurant.getCity());
        existing.setDistrict(restaurant.getDistrict());
        existing.setLongitude(restaurant.getLongitude());
        existing.setLatitude(restaurant.getLatitude());
        existing.setDetailedAddress(restaurant.getDetailedAddress());
        existing.setMenu(restaurant.getMenu());
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateOwner(Restaurant restaurant) {
        var existing = getById(restaurant.getId());
        if (doesRestaurantExistByUser(restaurant)) {
            throw new RuntimeException("User already owner of a restaurant!");
        }
        existing.setUser(restaurant.getUser());
        return restaurantRepository.save(restaurant);
    }

    public boolean doesRestaurantExistByName(Restaurant restaurant) {
        return restaurantRepository.findRestaurantByRestaurantName(restaurant.getRestaurantName()).isPresent();
    }

    public boolean doesRestaurantExistByUser(Restaurant restaurant) {
        return restaurantRepository.findRestaurantByUser(restaurant.getUser()).isPresent();
    }

    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("restaurant not found!"));
    }

    public void delete(Long id) {
        var restaurant = getById(id);
        restaurantRepository.delete(restaurant);
    }

}
