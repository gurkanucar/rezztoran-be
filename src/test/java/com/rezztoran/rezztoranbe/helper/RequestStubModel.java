package com.rezztoran.rezztoranbe.helper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Review request model. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestStubModel {

  private Long id;

  @NotNull private Long restaurantId;

  @NotNull private Long userId;

  private RequestUser requestUser;

  @NotBlank private String content;
}

