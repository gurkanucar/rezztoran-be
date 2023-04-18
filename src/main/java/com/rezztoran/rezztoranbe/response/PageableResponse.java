package com.rezztoran.rezztoranbe.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResponse<T> {
  private List<T> content;
  private int page;
  private int pageSize;
  private long totalElements;
  private int totalPages;

  public static <T> PageableResponse<T> fromPage(Page<T> page) {
    return new PageableResponse<>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages());
  }
}
