package com.rezztoran.rezztoranbe.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rezztoran.rezztoranbe.enums.ResponseConstants;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"status", "code", "message", "isPageable", "content", "error"})
public class ApiResponse {

  @JsonIgnore private HttpHeaders httpHeaders;
  private HttpStatus status;
  private int code;
  private String message;
  private Boolean isPageable;
  private Object error;
  private Object content;

  public static Builder builder(HttpStatus status, ResponseConstants responseConstants) {
    return new Builder(status, responseConstants);
  }

  public static Builder builder() {
    return new Builder();
  }

  private ApiResponse(Builder builder) {
    this.status = builder.status;
    this.httpHeaders = builder.httpHeaders;
    this.code = builder.code;
    this.isPageable = builder.isPageable;
    this.content = builder.content;
    this.message = builder.message;
    this.error = builder.error;
  }

  public static class Builder {

    private HttpHeaders httpHeaders = new HttpHeaders();
    private HttpStatus status;
    private int code;
    private Boolean isPageable;
    private Object content;
    private Object error;
    private String message;

    public Builder(HttpStatus status, ResponseConstants responseConstants) {
      this.status = status;
      this.code = responseConstants.getCode();
      this.message = responseConstants.getMessage();
      this.isPageable = false;
    }

    public Builder() {
      this.status = HttpStatus.OK;
      this.code = ResponseConstants.SUCCESS.getCode();
      this.message = ResponseConstants.SUCCESS.getMessage();
      this.isPageable = false;
    }

    public Builder header(String name, String value) {
      this.httpHeaders.add(name, value);
      return this;
    }

    public Builder data(Object object) {
      this.content = object;
      return this;
    }

    public Builder pageableData(Object object) {
      this.isPageable = true;
      this.content = object;
      return this;
    }

    public Builder error(Object obj, HttpStatus status) {
      this.code = ResponseConstants.FAILURE.getCode();
      this.status = status;
      this.message = null;
      this.error = obj;
      this.isPageable = null;
      return this;
    }

    public ResponseEntity<ApiResponse> build() {
      ApiResponse apiResponse = new ApiResponse(this);
      return ResponseEntity.status(status).headers(httpHeaders).body(apiResponse);
    }
  }
}
