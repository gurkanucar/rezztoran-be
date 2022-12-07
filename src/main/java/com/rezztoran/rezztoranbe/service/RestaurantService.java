package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.GenericErrorResponse;
import com.rezztoran.rezztoranbe.exception.NotFoundException;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;


    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }


    public Restaurant create(Restaurant restaurant) {
        if (doesRestaurantExistByName(restaurant)) {
            throw new GenericErrorResponse("Restaurant already exists!", HttpStatus.CONFLICT);
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
        return restaurantRepository.save(existing);
    }

    public Restaurant updateOwner(Restaurant restaurant) {
        var existing = getById(restaurant.getId());
        if (doesRestaurantExistByUser(restaurant)) {
            throw new GenericErrorResponse("User already owner of a restaurant!", HttpStatus.CONFLICT);
        }
        var user = userService.findUserByID(restaurant.getUser().getId());
        existing.setUser(user);
        return restaurantRepository.save(existing);
    }

    public boolean doesRestaurantExistByName(Restaurant restaurant) {
        return restaurantRepository.findRestaurantByRestaurantName(restaurant.getRestaurantName()).isPresent();
    }

    public boolean doesRestaurantExistByUser(Restaurant restaurant) {
        return restaurantRepository.findRestaurantByUser(restaurant.getUser()).isPresent();
    }

    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("restaurant not found!"));
    }

    public void delete(Long id) {
        var restaurant = getById(id);
        restaurantRepository.delete(restaurant);
    }

}
