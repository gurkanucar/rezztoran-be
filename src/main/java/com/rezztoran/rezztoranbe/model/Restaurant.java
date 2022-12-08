package com.rezztoran.rezztoranbe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToOne
    private User user;


    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Review> reviews;


}
