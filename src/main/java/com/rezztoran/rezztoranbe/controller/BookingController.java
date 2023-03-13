package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.model.Booking;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.BookService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @GetMapping("/user/{id}")
  public ResponseEntity<ApiResponse<Object>> getBooksByUserId(
      @PathVariable Long id,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
    List<Booking> response;
    if (localDate == null) {
      response = bookService.getBooksByUser(id);
    } else {
      response = bookService.getBooksByUserAndDate(id, localDate);
    }
    return ApiResponse.builder().data(response).build();
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
