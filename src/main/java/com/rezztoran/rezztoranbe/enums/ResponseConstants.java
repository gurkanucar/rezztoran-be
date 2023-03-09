package com.rezztoran.rezztoranbe.enums;

public enum ResponseConstants {
  SUCCESS(0, "Success"),
  FAILURE(-1, "Failure");

  final int code;
  final String message;

  ResponseConstants(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
