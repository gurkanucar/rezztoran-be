package com.rezztoran.rezztoranbe.exception;

import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalBaseExceptionHandler extends BaseExceptionHandler {

  private final ExceptionUtil exceptionUtil;

  @ExceptionHandler(BusinessException.class)
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public ResponseEntity<ApiResponse<Object>> generalException(BusinessException exception) {
    return buildErrorResponse(exception.getMessage(), exception.getStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidEx(
      MethodArgumentNotValidException ex, WebRequest request) {
    return getMapResponseEntity(ex);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity<ApiResponse<Object>> handleConstraintViolationEx(
      MethodArgumentNotValidException ex, WebRequest request) {
    return getMapResponseEntity(ex);
  }

  private ResponseEntity<ApiResponse<Object>> getMapResponseEntity(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            x -> {
              String fieldName = ((FieldError) x).getField();
              String errorMessage = x.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return buildErrorResponse(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ClientRequestException.class)
  public ResponseEntity<ApiResponse<Object>> generalException(ClientRequestException exception) {
    return buildErrorResponse(exception.getMessage(), exception.getStatus());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public final ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(
      AccessDeniedException ex, WebRequest request) {
    // return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    throw exceptionUtil.buildException(Ex.ACCESS_DENIED_EXCEPTION);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ApiResponse<Object>> handleAllException(
      Exception ex, WebRequest request) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public final ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(
      Exception ex, WebRequest request) {
    return buildErrorResponse("Required request body is missing", HttpStatus.BAD_REQUEST);
  }
}
