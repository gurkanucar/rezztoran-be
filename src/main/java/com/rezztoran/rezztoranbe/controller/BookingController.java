package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.BookService;
import java.time.LocalDate;
import java.util.List;
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
    List<BookDTO> response;
    if (localDate == null) {
      response = bookService.getBooksByUser(id);
    } else {
      response = bookService.getBooksByUserAndDate(id, localDate);
    }
    return ApiResponse.builder().data(response).build();
  }

  /**
   * Create booking response entity.
   *
   * @param bookRequestModel the book request model
   * @return the response entity
   */
  @PostMapping
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
   * get booking response entity.
   *
   * @param id the id
   * @return the response entity
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
   * Delete booking response entity.
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
