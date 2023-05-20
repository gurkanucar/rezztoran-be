package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.BaseEntity;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.RestaurantRepository;
import com.rezztoran.rezztoranbe.service.impl.AuthServiceImpl;
import com.rezztoran.rezztoranbe.service.impl.UserServiceImpl;
import com.rezztoran.rezztoranbe.spesifications.RestaurantSpecifications;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/** The type Restaurant service. */
@Service
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final UserServiceImpl userService;
  private final AuthServiceImpl authService;
  private final FavoriteRestaurantService favoriteRestaurantService;
  private final ExceptionUtil exceptionUtil;
  private final ModelMapper mapper;
  private final ReviewService reviewService;

  /**
   * Instantiates a new Restaurant service.
   *
   * @param restaurantRepository the restaurant repository
   * @param userService the user service
   * @param authService the auth service
   * @param favoriteRestaurantService the favorite restaurant service
   * @param exceptionUtil the exception util
   * @param mapper the mapper
   * @param reviewService the review service
   */
  public RestaurantService(
      RestaurantRepository restaurantRepository,
      UserServiceImpl userService,
      AuthServiceImpl authService,
      @Lazy FavoriteRestaurantService favoriteRestaurantService,
      ExceptionUtil exceptionUtil,
      ModelMapper mapper,
      @Lazy ReviewService reviewService) {
    this.restaurantRepository = restaurantRepository;
    this.userService = userService;
    this.authService = authService;
    this.favoriteRestaurantService = favoriteRestaurantService;
    this.exceptionUtil = exceptionUtil;
    this.mapper = mapper;
    this.reviewService = reviewService;
  }

  /**
   * Gets restaurants.
   *
   * @param searchTerm the search term
   * @param sortField the sort field
   * @param sortDirection the sort direction
   * @param city the city
   * @param restaurantName the restaurant name
   * @param district the district
   * @param categories the categories
   * @param localDate the local date
   * @param pageable the pageable
   * @return the restaurants
   */
  public Page<RestaurantDTO> getRestaurants(
      String searchTerm,
      String sortField,
      Sort.Direction sortDirection,
      String city,
      String restaurantName,
      String district,
      List<String> categories,
      LocalDate localDate,
      Pageable pageable) {

    Specification<Restaurant> spec =
        Specification.where(
                RestaurantSpecifications.searchByCityDistrictOrName(
                    searchTerm, city, restaurantName, district, localDate, categories))
            .and(RestaurantSpecifications.sortBySelectedFields(sortField, sortDirection));

    Page<RestaurantDTO> restaurantPage =
        restaurantRepository.findAll(spec, pageable).map(x -> mapper.map(x, RestaurantDTO.class));

    var ids =
        restaurantPage.getContent().stream().map(RestaurantDTO::getId).collect(Collectors.toList());

    var starCounts = reviewService.calculateStarCountByRestaurant(ids);

    restaurantPage
        .getContent()
        .forEach(
            x -> {
              x.setStarCount(starCounts.get(x.getId()) == null ? -1 : starCounts.get(x.getId()));
            });
    // if user is empty return without isFavorite field
    var user = authService.getAuthenticatedUser();
    if (user.isEmpty()) {
      return restaurantPage;
    }
    // get favorite restaurants of user
    Set<Long> favoriteRestaurantIds =
        favoriteRestaurantService.getFavoriteRestaurantsByUser(user.get().getId()).stream()
            .map(BaseEntity::getId)
            .collect(Collectors.toSet());

    restaurantPage
        .getContent()
        .forEach(x -> x.setIsFavorite(favoriteRestaurantIds.contains(x.getId())));

    return restaurantPage;
  }

  /**
   * Gets restaurants randomly.
   *
   * @param count the count
   * @return the restaurants randomly
   */
  public List<RestaurantDTO> getRestaurantsRandomly(int count) {
    return restaurantRepository.findAllRandomly(count).stream()
        .map(x -> mapper.map(x, RestaurantDTO.class))
        .collect(Collectors.toList());
  }

  /**
   * Create restaurant.
   *
   * @param restaurant the restaurant
   * @return the restaurant
   */
  public Restaurant create(Restaurant restaurant) {
    if (doesRestaurantExistByName(restaurant)) {
      throw exceptionUtil.buildException(Ex.RESTAURANT_ALREADY_EXISTS_EXCEPTION);
    }
    return restaurantRepository.save(restaurant);
  }

  /**
   * Create.
   *
   * @param restaurants the restaurants
   */
  public void create(List<Restaurant> restaurants) {
    restaurants.forEach(
        x -> {
          if (!doesRestaurantExistByName(x)) {
            create(x);
          }
        });
  }

  /**
   * Update restaurant.
   *
   * @param restaurant the restaurant
   * @return the restaurant
   */
  @Transactional
  public Restaurant update(Restaurant restaurant) {
    var existing = getById(restaurant.getId());
    existing.setCity(restaurant.getCity());
    existing.setDistrict(restaurant.getDistrict());
    existing.setLongitude(restaurant.getLongitude());
    existing.setLatitude(restaurant.getLatitude());
    existing.setRestaurantImage(restaurant.getRestaurantImage());
    existing.setRestaurantImageList(restaurant.getRestaurantImageList());
    existing.setDetailedAddress(restaurant.getDetailedAddress());
    existing.setPhone(restaurant.getPhone());
    existing.setBookingAvailable(restaurant.getBookingAvailable());
    existing.setOpeningTime(restaurant.getOpeningTime());
    existing.setClosingTime(restaurant.getClosingTime());
    existing.setIntervalMinutes(restaurant.getIntervalMinutes());
    existing.setBusyDates(restaurant.getBusyDates());
    existing.setRestaurantAttributes(restaurant.getRestaurantAttributes());
    return restaurantRepository.save(existing);
  }

  /**
   * Update owner restaurant.
   *
   * @param restaurant the restaurant
   * @return the restaurant
   */
  public Restaurant updateOwner(Restaurant restaurant) {
    var existing = getById(restaurant.getId());
    if (doesRestaurantExistByUser(restaurant)) {
      throw exceptionUtil.buildException(Ex.USER_ALREADY_OWNER_OF_A_RESTAURANT_EXCEPTION);
    }
    var user = userService.findUserByID(restaurant.getUser().getId());
    existing.setUser(user);
    return restaurantRepository.save(existing);
  }

  /**
   * Does restaurant exist by name boolean.
   *
   * @param restaurant the restaurant
   * @return the boolean
   */
  public boolean doesRestaurantExistByName(Restaurant restaurant) {
    return restaurantRepository
        .findRestaurantByRestaurantName(restaurant.getRestaurantName())
        .isPresent();
  }

  /**
   * Does restaurant exist by user boolean.
   *
   * @param restaurant the restaurant
   * @return the boolean
   */
  public boolean doesRestaurantExistByUser(Restaurant restaurant) {
    return restaurantRepository.findRestaurantByUser(restaurant.getUser()).isPresent();
  }

  /**
   * Gets by id.
   *
   * @param id the id
   * @return the by id
   */
  public Restaurant getById(Long id) {
    return restaurantRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.RESTAURANT_NOT_FOUND_EXCEPTION));
  }

  /**
   * Gets by id dto.
   *
   * @param id the id
   * @return the by id dto
   */
  public RestaurantDTO getByIdDto(Long id) {
    var score = reviewService.calculateStarCountByRestaurant(id);
    var user = authService.getAuthenticatedUser();
    if (user.isEmpty()) {
      var restaurant =
          restaurantRepository
              .findById(id)
              .orElseThrow(() -> exceptionUtil.buildException(Ex.RESTAURANT_NOT_FOUND_EXCEPTION));
      return mapper.map(restaurant, RestaurantDTO.class);
    }
    var favoriteRestaurant =
        favoriteRestaurantService.getFavoriteRestaurantByUserAndRestaurantId(
            user.get().getId(), id);
    var restaurant =
        restaurantRepository
            .findById(id)
            .orElseThrow(() -> exceptionUtil.buildException(Ex.RESTAURANT_NOT_FOUND_EXCEPTION));
    var restaurantDto = mapper.map(restaurant, RestaurantDTO.class);

    restaurantDto.setStarCount(score);
    restaurantDto.setIsFavorite(favoriteRestaurant.isPresent());

    return restaurantDto;
  }

  /**
   * Delete.
   *
   * @param id the id
   */
  public void delete(Long id) {
    var restaurant = getById(id);
    restaurantRepository.delete(restaurant);
  }
}
