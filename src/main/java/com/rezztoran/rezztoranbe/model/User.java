package com.rezztoran.rezztoranbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rezztoran.rezztoranbe.enums.Role;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The type User. */
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "`user`")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String username;

  @JsonIgnore private String password;
  private String name;
  private String surname;

  @Email
  @Column(nullable = false, unique = true)
  private String mail;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  private List<Review> reviews;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "password_reset_info_id", referencedColumnName = "id")
  private PasswordResetInfo passwordResetInfo;

}
