package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.BaseEntity;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.RestaurantRepository;
import com.rezztoran.rezztoranbe.service.BookService;
import com.rezztoran.rezztoranbe.service.FavoriteRestaurantService;
import com.rezztoran.rezztoranbe.service.QRCodeService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.ReviewService;
import com.rezztoran.rezztoranbe.spesifications.RestaurantSpecifications;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/** The type Restaurant service. */
@Service
@CacheConfig(cacheNames = {"restaurants"})
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final UserServiceImpl userService;
  private final AuthServiceImpl authService;
  private final FavoriteRestaurantService favoriteRestaurantService;
  private final ExceptionUtil exceptionUtil;
  private final ModelMapper mapper;
  private final ReviewService reviewService;
  private final QRCodeService qrCodeService;
  private final BookService bookService;

  @Value("${app-context}")
  private String appContext;

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
   * @param qrCodeService the qr code service
   * @param bookService the book service
   */
  public RestaurantServiceImpl(
      RestaurantRepository restaurantRepository,
      UserServiceImpl userService,
      AuthServiceImpl authService,
      @Lazy FavoriteRestaurantService favoriteRestaurantService,
      ExceptionUtil exceptionUtil,
      ModelMapper mapper,
      @Lazy ReviewService reviewService,
      QRCodeService qrCodeService,
      @Lazy BookService bookService) {
    this.restaurantRepository = restaurantRepository;
    this.userService = userService;
    this.authService = authService;
    this.favoriteRestaurantService = favoriteRestaurantService;
    this.exceptionUtil = exceptionUtil;
    this.mapper = mapper;
    this.reviewService = reviewService;
    this.qrCodeService = qrCodeService;
    this.bookService = bookService;
  }

  @CacheEvict(allEntries = true)
  @PostConstruct
  @Scheduled(fixedRateString = "${cache-config.restaurant-cache-ttl}")
  public void clearCache() {
    //log.info("Caches are cleared");
  }

  @Override
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
            x ->
                x.setStarCount(starCounts.get(x.getId()) == null ? -1 : starCounts.get(x.getId())));
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

  @Override
  @Cacheable(key = "#randomCount")
  public List<RestaurantDTO> getRestaurantsRandomly(int randomCount) {
    return restaurantRepository.findAllRandomly(randomCount).stream()
        .map(x -> mapper.map(x, RestaurantDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public Restaurant create(Restaurant restaurant) {
    if (doesRestaurantExistByName(restaurant)) {
      throw exceptionUtil.buildException(Ex.RESTAURANT_ALREADY_EXISTS_EXCEPTION);
    }
    return restaurantRepository.save(restaurant);
  }

  @Override
  public void create(List<Restaurant> restaurants) {
    restaurants.forEach(
        x -> {
          if (!doesRestaurantExistByName(x)) {
            create(x);
          }
        });
  }

  @Transactional
  @Override
  public Restaurant update(Restaurant restaurant) {
    var existing = getById(restaurant.getId());
    existing.setCity(restaurant.getCity());
    existing.setDistrict(restaurant.getDistrict());
    existing.setLongitude(restaurant.getLongitude());
    existing.setLatitude(restaurant.getLatitude());
    existing.setRestaurantImage(restaurant.getRestaurantImage());
    existing.setRestaurantName(restaurant.getRestaurantName());
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

  @Override
  public Restaurant updateOwner(Restaurant restaurant) {
    var existing = getById(restaurant.getId());
    if (doesRestaurantExistByUser(restaurant)) {
      throw exceptionUtil.buildException(Ex.USER_ALREADY_OWNER_OF_A_RESTAURANT_EXCEPTION);
    }
    var user = userService.findUserByID(restaurant.getUser().getId());
    existing.setUser(user);
    return restaurantRepository.save(existing);
  }

  @Override
  public boolean doesRestaurantExistByName(Restaurant restaurant) {
    return restaurantRepository
        .findRestaurantByRestaurantName(restaurant.getRestaurantName())
        .isPresent();
  }

  @Override
  public boolean doesRestaurantExistByUser(Restaurant restaurant) {
    return restaurantRepository.findRestaurantByUser(restaurant.getUser()).isPresent();
  }

  @Override
  public Restaurant getById(Long id) {
    return restaurantRepository
        .findByIdAndDeletedFalse(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.RESTAURANT_NOT_FOUND_EXCEPTION));
  }

  @Override
  public RestaurantDTO getByIdDto(Long id) {
    var score = reviewService.calculateStarCountByRestaurant(id);
    var user = authService.getAuthenticatedUser();
    if (user.isEmpty()) {
      var restaurant =
          restaurantRepository
              .findByIdAndDeletedFalse(id)
              .orElseThrow(() -> exceptionUtil.buildException(Ex.RESTAURANT_NOT_FOUND_EXCEPTION));
      return mapper.map(restaurant, RestaurantDTO.class);
    }
    var favoriteRestaurant =
        favoriteRestaurantService.getFavoriteRestaurantByUserAndRestaurantId(
            user.get().getId(), id);
    var restaurant =
        restaurantRepository
            .findByIdAndDeletedFalse(id)
            .orElseThrow(() -> exceptionUtil.buildException(Ex.RESTAURANT_NOT_FOUND_EXCEPTION));
    var restaurantDto = mapper.map(restaurant, RestaurantDTO.class);

    restaurantDto.setStarCount(score);
    restaurantDto.setIsFavorite(favoriteRestaurant.isPresent());

    return restaurantDto;
  }

  @Override
  public void delete(Long id) {
    var restaurant = getById(id);
    bookService.cancelAllBookingsByRestaurant(id);
    restaurant.setDeleted(true);
    restaurantRepository.save(restaurant);
  }

  @Override
  public byte[] generateQrCodeForRestaurant(Long id) {
    var restaurant = getById(id);
    String qrCodeText = appContext + "/api/food/restaurant/" + restaurant.getId();
    byte[] qrCode;
    try {
      qrCode = qrCodeService.generateQRCodeWithLogo(qrCodeText, 200, 200, true);
    } catch (Exception e) {
      throw exceptionUtil.buildException(Ex.DEFAULT_EXCEPTION);
    }
    restaurant.setQrCode(qrCode);
    restaurantRepository.save(restaurant);
    return qrCode;
  }
}
