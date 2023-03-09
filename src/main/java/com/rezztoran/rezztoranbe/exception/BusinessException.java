package com.rezztoran.rezztoranbe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
  private final HttpStatus status;

  BusinessException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public enum Exception {
    DEFAULT_EXCEPTION("messages.error.default_message", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS_EXCEPTION("messages.error.already_exists_exception", HttpStatus.BAD_REQUEST),
    NOT_FOUND_EXCEPTION("messages.error.not_found_exception", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION("messages.error.user_not_found_exception", HttpStatus.NOT_FOUND),
    FOOD_NOT_FOUND_EXCEPTION("messages.error.food_not_found_exception", HttpStatus.NOT_FOUND),
    USER_ALREADY_OWNER_OF_A_RESTAURANT_EXCEPTION(
        "messages.error.user_already_owner_of_a_restaurant_exception", HttpStatus.BAD_REQUEST),
    RESTAURANT_NOT_FOUND_EXCEPTION(
        "messages.error.restaurant_not_found_exception", HttpStatus.NOT_FOUND),
    WRONG_CREDENTIALS_EXCEPTION("messages.error.wrong_credentials_exception", HttpStatus.FORBIDDEN),
    MENU_NOT_FOUND_EXCEPTION("messages.error.menu_not_found_exception", HttpStatus.NOT_FOUND),
    RESTAURANT_ALREADY_EXISTS_EXCEPTION(
        "messages.error.restaurant_already_exists_exception", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS_EXCEPTION(
        "messages.error.user_already_exists_exception", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND_EXCEPTION(
        "messages.error.category_not_found_exception", HttpStatus.NOT_FOUND),
    CATEGORY_ALREADY_EXISTS_EXCEPTION(
        "messages.error.category_already_exists_exception", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    Exception(String message, HttpStatus status) {
      this.message = message;
      this.status = status;
    }

    public String getMessage() {
      return message;
    }

    public HttpStatus getStatus() {
      return status;
    }
  }
}
