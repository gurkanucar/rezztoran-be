package com.rezztoran.rezztoranbe.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestModel {

  private Long id;

  @NotNull private Long restaurantId;

  @NotNull private Long userId;

  @NotBlank private String content;

  @Min(0)
  @Max(5)
  private int star;
}
