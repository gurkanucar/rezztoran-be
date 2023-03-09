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

    @ElementCollection
    @CollectionTable(name = "restaurant_image_list", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "restaurant_image_list")
    private List<String> restaurantImageList;

    private String city;
    private String district;
    private String detailedAddress;
    private Double latitude;
    private Double longitude;
    private Double starCount;
    private String phone;


    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<RestaurantTable> restaurantTables;

    @OneToOne
    private User user;


    @OneToOne(mappedBy = "restaurant")
    private Menu menu;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Review> reviews;


}
