package com.rezztoran.rezztoranbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Mail model.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailModel {
  private String to;
  private String subject;
  private String text;
}
