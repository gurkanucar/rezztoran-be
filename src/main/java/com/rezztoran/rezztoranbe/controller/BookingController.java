package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.BookService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.time.LocalDate;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The type Booking controller. */
@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookingController {

  private final BookService bookService;

  /**
   * Gets books by user id.
   *
   * @param id the id
   * @param localDate the local date
   * @return the books by user id
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<ApiResponse<Object>> getBooksByUserId(
      @PathVariable Long id,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
    return ApiResponse.builder().data(bookService.getBooksByUserAndDate(id, localDate)).build();
  }

  /**
   * Create booking response entity.
   *
   * @param bookRequestModel the book request model
   * @return the response entity
   */
  @PostMapping
  @RateLimiter(name = "basic")
  public ResponseEntity<ApiResponse<Object>> createBooking(
      @Valid @RequestBody BookRequestModel bookRequestModel) {
    return ApiResponse.builder().data(bookService.createBook(bookRequestModel)).build();
  }

  /**
   * Update booking response entity.
   *
   * @param bookRequestModel the book request model
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateBooking(
      @Valid @RequestBody BookRequestModel bookRequestModel) {
    return ApiResponse.builder().data(bookService.updateBook(bookRequestModel)).build();
  }

  /**
   * Gets booking by id.
   *
   * @param id the id
   * @return the booking by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getBookingById(@PathVariable Long id) {
    return ApiResponse.builder().data(bookService.getBookingByIdAndAuth(id)).build();
  }

  /**
   * Delete booking response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteBooking(@PathVariable Long id) {
    bookService.deleteBook(id);
    return ApiResponse.builder().build();
  }

  /**
   * Delete booking by restaurant response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @PreAuthorize("hasAnyAuthority('ADMIN', 'RESTAURANT_ADMIN')")
  @DeleteMapping("/restaurant/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteBookingByRestaurant(@PathVariable Long id) {
    bookService.deleteBookByRestaurant(id);
    return ApiResponse.builder().build();
  }
}
