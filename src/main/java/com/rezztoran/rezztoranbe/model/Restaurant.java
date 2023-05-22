package com.rezztoran.rezztoranbe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The type Restaurant. */
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Restaurant extends BaseEntity {

  private String restaurantName;
  private String restaurantImage;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "restaurant_image_list", joinColumns = @JoinColumn(name = "id"))
  @Column(name = "restaurant_image_list")
  private List<String> restaurantImageList;

  private String city;
  private String district;
  private String detailedAddress;
  private Double latitude;
  private Double longitude;
  private String phone;

  @OneToOne private User user;

  @Lob
  @Column(columnDefinition = "BLOB")
  private byte[] qrCode;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Food> foods;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Review> reviews;

  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "name")
  @Column(name = "value")
  @CollectionTable(
      name = "restaurant_attributes",
      joinColumns = @JoinColumn(name = "restaurant_id"))
  private Map<String, String> restaurantAttributes = new HashMap<>();

  private Boolean bookingAvailable;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime openingTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime closingTime;

  // default 30 minutes
  private Integer intervalMinutes = 30;

  @ElementCollection
  @CollectionTable(name = "busy_dates", joinColumns = @JoinColumn(name = "id"))
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private List<LocalDate> busyDates;

  private boolean deleted;

  private Double starCount;
  private Integer reviewsCount;
}
