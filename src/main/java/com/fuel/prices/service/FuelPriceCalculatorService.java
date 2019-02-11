package com.fuel.prices.service;

import com.fuel.prices.model.CalculationResult;
import com.fuel.prices.model.FuelType;
import java.time.LocalDate;

public interface FuelPriceCalculatorService {

  CalculationResult calculate(LocalDate localDate, FuelType fuelType, int milesPerGallon, int mileage);
}
