package com.rezztoran.rezztoranbe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The type Food. */
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food extends BaseEntity {

  private String foodName;
  private String foodImage;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ManyToMany
  private List<Category> categories;

  @OneToOne private Category mainCategory;

  private Double price;
  private Double cal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id", nullable = false)
  private Menu menu;
}
