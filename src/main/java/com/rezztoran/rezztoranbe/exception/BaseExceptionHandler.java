package com.rezztoran.rezztoranbe.exception;

import com.rezztoran.rezztoranbe.enums.ResponseConstants;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The type Base exception handler.
 *
 * @param <T> the type parameter
 */
public abstract class BaseExceptionHandler<T> {

  /**
   * Build error response response entity.
   *
   * @param error the error
   * @param status the status
   * @return the response entity
   */
  protected final ResponseEntity<ApiResponse<Object>> buildErrorResponse(
      T error, HttpStatus status) {
    return ApiResponse.builder(status, ResponseConstants.FAILURE).error(error, status).build();
  }
}
