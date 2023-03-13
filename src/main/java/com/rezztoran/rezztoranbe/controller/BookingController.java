package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.BookService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookingController {

  private final BookService bookService;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getBooksByRestaurantIdAndDate(
      @PathVariable Long id,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date localDate) {
    return ResponseEntity.ok().build();
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createBooking(
      @RequestBody BookRequestModel bookRequestModel) {
    return ApiResponse.builder().data(bookService.createBook(bookRequestModel)).build();
  }

  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateBooking(
      @RequestBody BookRequestModel bookRequestModel) {
    return ApiResponse.builder().data(bookService.updateBook(bookRequestModel)).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteBooking(@PathVariable Long id) {
    bookService.deleteBook(id);
    return ApiResponse.builder().build();
  }
}
