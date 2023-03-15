package com.rezztoran.rezztoranbe.config;

import com.rezztoran.rezztoranbe.enums.Role;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.service.impl.UserServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupConfig implements CommandLineRunner {

  @Autowired private UserServiceImpl userService;

  @Override
  public void run(String... args) throws Exception {
    var admins =
        List.of(
            User.builder()
                .username("grkn")
                .password("pass")
                .mail("gurkanucar@yaani.com")
                .name("grkn")
                .surname("u.")
                .build(),
            User.builder()
                .username("alperen")
                .password("pass")
                .mail("sefalperen21@gmail.com")
                .name("alperen")
                .surname("k.")
                .build(),
            User.builder()
                .username("vadim")
                .password("pass")
                .mail("vadimkiniabaev@gmail.com")
                .name("vadim")
                .surname("k.")
                .build());

    var restaurantAdmins =
        List.of(
            User.builder()
                .username("rAdmin1")
                .password("pass")
                .mail("yixap83326@kaudat.com")
                .name("restaurant admini 1")
                .surname("soyad")
                .build(),
            User.builder()
                .username("rAdmin2")
                .password("pass")
                .mail("vajaham443@kaudat.com")
                .name("restaurant admini 2")
                .surname("soyad")
                .build());
    var users =
        List.of(
            User.builder()
                .username("user")
                .password("pass")
                .mail("test12345@kaudat.com")
                .name("user")
                .surname("soyad")
                .build());
    admins.forEach(
        x -> {
          try {
            userService.create(x, Role.ADMIN);
          } catch (Exception e) {
          }
        });

    restaurantAdmins.forEach(
        x -> {
          try {
            userService.create(x, Role.RESTAURANT_ADMIN);
          } catch (Exception e) {
          }
        });
    users.forEach(
        x -> {
          try {
            userService.create(x, Role.USER);
          } catch (Exception e) {
          }
        });
  }
}
