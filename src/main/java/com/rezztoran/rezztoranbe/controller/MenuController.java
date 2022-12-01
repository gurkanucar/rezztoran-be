package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.model.Menu;
import com.rezztoran.rezztoranbe.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuByID(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }


    @PostMapping
    public ResponseEntity<Menu> create(@RequestBody Menu menu) {
        return ResponseEntity.ok(menuService.create(menu));
    }

    @PutMapping
    public ResponseEntity<Menu> update(@RequestBody Menu menu) {
        return ResponseEntity.ok(menuService.update(menu));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.ok().build();
    }

}
