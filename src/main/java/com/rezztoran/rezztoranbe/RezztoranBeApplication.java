package com.rezztoran.rezztoranbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/** The type Rezztoran be application. */
@SpringBootApplication
@EnableAsync
@EnableScheduling
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
