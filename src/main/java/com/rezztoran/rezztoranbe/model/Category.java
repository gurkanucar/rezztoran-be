package com.rezztoran.rezztoranbe.model;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The type Category. */
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category extends BaseEntity {

  private String categoryName;
  private String categoryImage;
}
