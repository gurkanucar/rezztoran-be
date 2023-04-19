package com.rezztoran.rezztoranbe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The type Menu. */
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Menu extends BaseEntity {

  @OneToOne(cascade = CascadeType.MERGE)
  private Restaurant restaurant;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu", cascade = CascadeType.ALL)
  private List<Food> foods;

  @Lob
  @Column(columnDefinition = "BLOB")
  private byte[] qrCode;
}
