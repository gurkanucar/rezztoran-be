package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.NotFoundException;
import com.rezztoran.rezztoranbe.model.Menu;
import com.rezztoran.rezztoranbe.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;

    public Menu create(Menu menu) {
        var restaurant = restaurantService.getById(menu.getRestaurant().getId());
        menu.setRestaurant(restaurant);
        menu.setMenuCode(UUID.randomUUID().toString());
        menu = menuRepository.save(menu);
        restaurant.setMenu(menu);
        restaurantService.update(restaurant);
        return menu;
    }

    public Menu update(Menu menu) {
        var restaurant = restaurantService.getById(menu.getRestaurant().getId());
        var existing = getMenuById(menu.getId());
        existing.setRestaurant(restaurant);
        return menuRepository.save(existing);
    }

    public void delete(Long id) {
        var existing = getMenuById(id);
        menuRepository.delete(existing);
    }

    public Menu getMenuById(Long id) {
        var menu = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("menu not found!"));
        return menu;
    }

}
