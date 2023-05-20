package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.model.Menu;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Menu controller. */
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;

  /**
   * Gets menu by id.
   *
   * @param id the id
   * @return the menu by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getMenuByID(@PathVariable Long id) {
    return ApiResponse.builder().data(menuService.getMenuDtoById(id)).build();
  }

  /**
   * Create response entity.
   *
   * @param menu the menu
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Object>> create(@RequestBody Menu menu) {
    return ApiResponse.builder().data(menuService.create(menu)).build();
  }

  /**
   * Update response entity.
   *
   * @param menu the menu
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<ApiResponse<Object>> update(@RequestBody Menu menu) {
    return ApiResponse.builder().data(menuService.update(menu)).build();
  }

  /**
   * Delete response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
    menuService.delete(id);
    return ApiResponse.builder().build();
  }
}
