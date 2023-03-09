package com.rezztoran.rezztoranbe.config;

import com.rezztoran.rezztoranbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupConfig implements CommandLineRunner {

  @Autowired private UserService userService;

  @Override
  public void run(String... args) throws Exception {
    //    userService.create(
    //        User.builder()
    //            .username("grkn")
    //            .password("pass")
    //            .mail("gurkan@mail")
    //            .name("grkn")
    //            .surname("ucar")
    //            .build(),
    //        Role.ADMIN);
  }
}
