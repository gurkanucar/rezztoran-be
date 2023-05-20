// package com.rezztoran.rezztoranbe.service;
//
// import static com.rezztoran.rezztoranbe.exception.BusinessException.Ex.ALREADY_EXISTS_EXCEPTION;
// import static com.rezztoran.rezztoranbe.exception.BusinessException.Ex.DEFAULT_EXCEPTION;
//
// import com.rezztoran.rezztoranbe.dto.MenuDTO;
// import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
// import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
// import com.rezztoran.rezztoranbe.model.Menu;
// import com.rezztoran.rezztoranbe.repository.MenuRepository;
// import javax.transaction.Transactional;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
//
/// ** The type Menu service. */
// @Service
// @RequiredArgsConstructor
// @Slf4j
// public class MenuService {
//
//  private final MenuRepository menuRepository;
//  private final RestaurantService restaurantService;
//  private final ExceptionUtil exceptionUtil;
//  private final QRCodeService qrCodeService;
//  private final ModelMapper mapper;
//
//  @Value("${app-context}")
//  private String appContext;
//
//  /**
//   * Create menu.
//   *
//   * @param menu the menu
//   * @return the menu
//   */
//  @Transactional
//  public MenuDTO create(Menu menu) {
//    var restaurant = restaurantService.getById(menu.getRestaurant().getId());
//    if (restaurant.getMenu() != null) {
//      throw exceptionUtil.buildException(ALREADY_EXISTS_EXCEPTION);
//    }
//    menu.setRestaurant(restaurant);
//    var result = setQRCode(menu, menuRepository.save(menu));
//    return convertToDto(result);
//  }
//
//  private Menu setQRCode(Menu menu, Menu result) {
//    try {
//      String qrCodeText = appContext + "/api/menu/" + result.getId();
//      byte[] qrCode = qrCodeService.generateQRCodeWithLogo(qrCodeText, 300, 300, true);
//      menu.setQrCode(qrCode);
//    } catch (Exception e) {
//      throw exceptionUtil.buildException(DEFAULT_EXCEPTION);
//    }
//    result = menuRepository.save(menu);
//    return result;
//  }
//
//  /**
//   * Update menu.
//   *
//   * @param menu the menu
//   * @return the menu
//   */
//  public MenuDTO update(Menu menu) {
//    var restaurant = restaurantService.getById(menu.getRestaurant().getId());
//    var existing = getMenuById(menu.getId());
//    existing.setRestaurant(restaurant);
//    var result = menuRepository.save(existing);
//    return convertToDto(result);
//  }
//
//  /**
//   * Delete.
//   *
//   * @param id the id
//   */
//  public void delete(Long id) {
//    var existing = getMenuById(id);
//    menuRepository.delete(existing);
//  }
//
//  /**
//   * Gets menu by id.
//   *
//   * @param id the id
//   * @return the menu by id
//   */
//  public Menu getMenuById(Long id) {
//    return menuRepository
//        .findById(id)
//        .orElseThrow(() -> exceptionUtil.buildException(Ex.NOT_FOUND_EXCEPTION));
//  }
//  /**
//   * Gets menuDto by id.
//   *
//   * @param id the id
//   * @return the menuDto by id
//   */
//  public MenuDTO getMenuDtoById(Long id) {
//    var result =
//        menuRepository
//            .findById(id)
//            .orElseThrow(() -> exceptionUtil.buildException(Ex.NOT_FOUND_EXCEPTION));
//    var dto = convertToDto(result);
//    dto.getFoods().forEach(x -> x.setMenu(null));
//    dto.getFoods().forEach(x -> x.setCategories(null));
//    return dto;
//  }
//
//  /**
//   * Gets menuDto by id.
//   *
//   * @param id the id
//   * @return the menuDto by id
//   */
//  public MenuDTO getMenuDtoByRestaurantId(Long id) {
//    var restaurant = restaurantService.getById(id);
//    return getMenuDtoById(restaurant.getMenu().getId());
//  }
//
//  private MenuDTO convertToDto(Menu result) {
//    if(result.getRestaurant()!=null){
//      result.getRestaurant().getMenu().setRestaurant(null);
//      result.getRestaurant().getMenu().setFoods(null);
//    }
//    return mapper.map(result, MenuDTO.class);
//  }
// }
