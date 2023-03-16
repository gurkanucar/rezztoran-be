package com.rezztoran.rezztoranbe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/** The type Business exception. */
@Getter
public class BusinessException extends RuntimeException {
  private final HttpStatus status;

  /**
   * Instantiates a new Business exception.
   *
   * @param message the message
   * @param status the status
   */
  public BusinessException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  /** The enum Ex. */
  public enum Ex {
    /** Default exception ex. */
    DEFAULT_EXCEPTION("messages.error.default_message", HttpStatus.BAD_REQUEST),
    /** Already exists exception ex. */
    ALREADY_EXISTS_EXCEPTION("messages.error.already_exists_exception", HttpStatus.BAD_REQUEST),
    /** Not found exception ex. */
    NOT_FOUND_EXCEPTION("messages.error.not_found_exception", HttpStatus.NOT_FOUND),
    /** User not found exception ex. */
    USER_NOT_FOUND_EXCEPTION("messages.error.user_not_found_exception", HttpStatus.NOT_FOUND),
    /** Access denied exception ex. */
    ACCESS_DENIED_EXCEPTION("messages.error.access_denied_exception", HttpStatus.UNAUTHORIZED),
    /** Forbidden exception ex. */
    FORBIDDEN_EXCEPTION("messages.error.forbidden_exception", HttpStatus.FORBIDDEN),
    /** Could not book exception ex. */
    COULD_NOT_BOOK_EXCEPTION("messages.error.could_not_book_exception", HttpStatus.BAD_REQUEST),
    /** Book not found exception ex. */
    BOOK_NOT_FOUND_EXCEPTION("messages.error.book_not_found_exception", HttpStatus.NOT_FOUND),
    /** Available book not found exception ex. */
    AVAILABLE_BOOK_NOT_FOUND_EXCEPTION(
        "messages.error.available_book_not_found_exception", HttpStatus.NOT_FOUND),
    /** Food not found exception ex. */
    FOOD_NOT_FOUND_EXCEPTION("messages.error.food_not_found_exception", HttpStatus.NOT_FOUND),
    /** User already owner of a restaurant exception ex. */
    USER_ALREADY_OWNER_OF_A_RESTAURANT_EXCEPTION(
        "messages.error.user_already_owner_of_a_restaurant_exception", HttpStatus.BAD_REQUEST),
    /** Restaurant not found exception ex. */
    RESTAURANT_NOT_FOUND_EXCEPTION(
        "messages.error.restaurant_not_found_exception", HttpStatus.NOT_FOUND),
    /** Wrong credentials exception ex. */
    WRONG_CREDENTIALS_EXCEPTION("messages.error.wrong_credentials_exception", HttpStatus.FORBIDDEN),
    /** Menu not found exception ex. */
    MENU_NOT_FOUND_EXCEPTION("messages.error.menu_not_found_exception", HttpStatus.NOT_FOUND),
    /** Restaurant already exists exception ex. */
    RESTAURANT_ALREADY_EXISTS_EXCEPTION(
        "messages.error.restaurant_already_exists_exception", HttpStatus.BAD_REQUEST),
    /** User already exists exception ex. */
    USER_ALREADY_EXISTS_EXCEPTION(
        "messages.error.user_already_exists_exception", HttpStatus.BAD_REQUEST),
    /** Category not found exception ex. */
    CATEGORY_NOT_FOUND_EXCEPTION(
        "messages.error.category_not_found_exception", HttpStatus.NOT_FOUND),
    /** Category already exists exception ex. */
    CATEGORY_ALREADY_EXISTS_EXCEPTION(
        "messages.error.category_already_exists_exception", HttpStatus.NOT_FOUND),
    /** Review not found exception ex. */
    REVIEW_NOT_FOUND_EXCEPTION("messages.error.review_not_found_exception", HttpStatus.NOT_FOUND),
    /** Review already exists exception ex. */
    REVIEW_ALREADY_EXISTS_EXCEPTION(
        "messages.error.review_already_exists_exception", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    Ex(String message, HttpStatus status) {
      this.message = message;
      this.status = status;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
      return message;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public HttpStatus getStatus() {
      return status;
    }
  }
}
