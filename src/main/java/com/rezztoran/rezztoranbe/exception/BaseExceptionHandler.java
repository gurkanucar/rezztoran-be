package com.rezztoran.rezztoranbe.exception;

import com.rezztoran.rezztoranbe.enums.ResponseConstants;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseExceptionHandler<T> {

  protected final ResponseEntity<ApiResponse<Object>> buildErrorResponse(
      T error, HttpStatus status) {
    return ApiResponse.builder(status, ResponseConstants.FAILURE).error(error, status).build();
  }
}
