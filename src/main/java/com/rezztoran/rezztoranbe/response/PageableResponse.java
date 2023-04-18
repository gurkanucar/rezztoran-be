package com.rezztoran.rezztoranbe.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * The type Pageable response.
 *
 * @param <T> the type parameter
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResponse<T> {
  private List<T> content;
  private int page;
  private int pageSize;
  private long totalElements;
  private int totalPages;

  /**
   * From page pageable response.
   *
   * @param <T> the type parameter
   * @param page the page
   * @return the pageable response
   */
  public static <T> PageableResponse<T> fromPage(Page<T> page) {
    return new PageableResponse<>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages());
  }
}
