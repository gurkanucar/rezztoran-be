package com.rezztoran.rezztoranbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rezztoran.rezztoranbe.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.util.List;

@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "`user`")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String surname;
    @Email
    private String mail;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    private boolean resetPassword;
    private Integer resetPasswordCode;

}
