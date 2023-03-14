package com.rezztoran.rezztoranbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RezztoranBeApplication {

  public static void main(String[] args) {
    SpringApplication.run(RezztoranBeApplication.class, args);
  }
}
