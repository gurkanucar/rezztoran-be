package com.rezztoran.rezztoranbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/** The type Rezztoran be application. */
@SpringBootApplication
@EnableAsync
public class RezztoranBeApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(RezztoranBeApplication.class, args);
  }
}
