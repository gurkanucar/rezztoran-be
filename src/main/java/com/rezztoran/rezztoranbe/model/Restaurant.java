package com.rezztoran.rezztoranbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

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
    private Double starCount;

    @OneToOne
    private Menu menu;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Review> reviews;


}
