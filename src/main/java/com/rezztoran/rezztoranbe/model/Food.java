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
public class Food extends BaseEntity {

    private String foodName;
    private String foodImage;

    @ManyToMany
    private List<Category> categories;

    @OneToOne
    private Category mainCategory;

    private Double price;
    private Double cal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;


}
