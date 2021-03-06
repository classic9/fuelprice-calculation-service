package com.fuel.prices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FuelPriceCalculationEngine {

  public static void main(String[] args) {
    SpringApplication.run(FuelPriceCalculationEngine.class, args);
  }
}

