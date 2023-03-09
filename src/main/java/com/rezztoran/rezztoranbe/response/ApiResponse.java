package com.rezztoran.rezztoranbe.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rezztoran.rezztoranbe.enums.ResponseConstants;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "code", "message", "isPageable", "content", "error"})
public class ApiResponse<T> {

  @JsonIgnore private HttpHeaders httpHeaders;
  private HttpStatus status;
  private int code;
  private String message;
  private Boolean isPageable;
  private T error;
  private T content;

  private ApiResponse(Builder<T> builder) {
    this.status = builder.status;
    this.httpHeaders = builder.httpHeaders;
    this.code = builder.code;
    this.isPageable = builder.isPageable;
    this.content = builder.content;
    this.message = builder.message;
    this.error = builder.error;
  }

  public static <T> Builder<T> builder(HttpStatus status, ResponseConstants responseConstants) {
    return new Builder<>(status, responseConstants);
  }

  public static <T> Builder<T> builder() {
    return new Builder<>();
  }

  public static class Builder<T> {

    private HttpHeaders httpHeaders = new HttpHeaders();
    private HttpStatus status;
    private int code;
    private Boolean isPageable = false;
    private T content;
    private T error;
    private String message;

    public Builder(HttpStatus status, ResponseConstants responseConstants) {
      this.status = status;
      this.code = responseConstants.getCode();
      this.message = responseConstants.getMessage();
    }

    public Builder() {
      this.status = HttpStatus.OK;
      this.code = ResponseConstants.SUCCESS.getCode();
      this.message = ResponseConstants.SUCCESS.getMessage();
    }

    public Builder<T> header(String name, String value) {
      this.httpHeaders.add(name, value);
      return this;
    }

    public Builder<T> data(T object) {
      this.content = object;
      return this;
    }

    public Builder<T> pageableData(T object) {
      this.isPageable = true;
      this.content = object;
      return this;
    }

    public Builder<T> error(T obj) {
      return error(obj, HttpStatus.BAD_REQUEST);
    }

    public Builder<T> error(T obj, HttpStatus status) {
      this.code = ResponseConstants.FAILURE.getCode();
      this.status = status;
      this.message = null;
      this.error = obj;
      this.isPageable = null;
      return this;
    }

    public ResponseEntity<ApiResponse<T>> build() {
      ApiResponse<T> apiResponse = new ApiResponse<>(this);
      return ResponseEntity.status(status).headers(httpHeaders).body(apiResponse);
    }
  }
}
