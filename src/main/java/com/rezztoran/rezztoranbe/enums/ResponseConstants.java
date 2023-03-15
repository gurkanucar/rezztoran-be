package com.rezztoran.rezztoranbe.enums;

/** The enum Response constants. */
public enum ResponseConstants {
  /** Success response constants. */
  SUCCESS(0, "Success"),
  /** Failure response constants. */
  FAILURE(-1, "Failure");

  /** The Code. */
  final int code;
  /** The Message. */
  final String message;

  ResponseConstants(int code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * Gets code.
   *
   * @return the code
   */
  public int getCode() {
    return code;
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }
}
