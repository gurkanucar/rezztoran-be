package com.rezztoran.rezztoranbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant extends BaseEntity {

    private String restaurantName;
    private String restaurantImage;
    private String city;
    private String district;
    private String detailedAddress;
    private Double latitude;
    private Double longitude;

    @OneToOne
    private Menu menu;


}
