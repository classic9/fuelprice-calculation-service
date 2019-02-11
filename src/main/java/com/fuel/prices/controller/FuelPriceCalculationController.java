package com.fuel.prices.controller;

import com.fuel.prices.model.CalculationResult;
import com.fuel.prices.model.FuelPrice;
import com.fuel.prices.model.FuelType;
import com.fuel.prices.model.ResponseTemplate;
import com.fuel.prices.repo.FuelPriceRepo;
import com.fuel.prices.service.FuelPriceCalculatorService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FuelPriceCalculationController {

  private final FuelPriceRepo fuelPriceRepo;
  private FuelPriceCalculatorService fuelPriceCalculatorService;

  @Autowired
  public FuelPriceCalculationController(FuelPriceRepo fuelPriceRepo) {
    this.fuelPriceRepo = fuelPriceRepo;
  }

  @GetMapping("/calculate/fuelprice")
  public ResponseTemplate<CalculationResult> calculateFuelPrice(
      @RequestParam("date") LocalDate date,
      @RequestParam("fuelType") FuelType fuelType,
      @RequestParam("milesPerGallon") int milesPerGallon,
      @RequestParam("mileage") int mileage) {

    ResponseTemplate<CalculationResult> result = new ResponseTemplate<>();
    result.setData(fuelPriceCalculatorService.calculate(date, fuelType, milesPerGallon, mileage));
    return new ResponseTemplate<>();
  }

  @GetMapping(value = "/read/fuelprice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public FuelPrice getFuelPriceForDate(
      @RequestParam("date")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

    return fuelPriceRepo.findTop1ByFromDateBeforeOrderByFromDateDesc(date);
  }
}
