package com.rezztoran.rezztoranbe.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors()
        .forEach(x -> errors.put(((FieldError) x).getField(), x.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<?> categoryNotFoundException(CategoryNotFoundException exception) {
    HashMap<String, String> errors = new HashMap<>();
    errors.put("error", exception.getMessage());
    return ResponseEntity.status(404).body(errors);
  }

  @ExceptionHandler(CategoryAlreadyExistsException.class)
  public ResponseEntity<?> categoryAlreadyExists(CategoryAlreadyExistsException exception) {
    HashMap<String, String> errors = new HashMap<>();
    errors.put("error", exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
  }

  @ExceptionHandler(WrongCredientalsException.class)
  public ResponseEntity<?> usernameOrPasswordInvalidException(WrongCredientalsException exception) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", exception.getMessage());
    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<?> userNotFoundException(UserNotFoundException exception) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", exception.getMessage());
    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> notFoundException(NotFoundException exception) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", exception.getMessage());
    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(GenericErrorResponse.class)
  public ResponseEntity<?> genericError(GenericErrorResponse exception) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", exception.getMessage());
    return new ResponseEntity<>(errors, exception.getHttpStatus());
  }


  @ExceptionHandler(Exception.class)
  public final ResponseEntity<?> handleAllException(Exception ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

}
