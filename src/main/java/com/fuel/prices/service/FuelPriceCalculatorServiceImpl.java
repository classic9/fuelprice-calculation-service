package com.fuel.prices.service;

import com.fuel.prices.model.CalculationResult;
import com.fuel.prices.model.FuelPrice;
import com.fuel.prices.model.FuelType;
import com.fuel.prices.repo.FuelPriceRepo;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuelPriceCalculatorServiceImpl implements FuelPriceCalculatorService {

  @Autowired
  private FuelPriceRepo fuelPriceRepo;

  @Override
  public CalculationResult calculate(LocalDate localDate, FuelType fuelType, int milesPerGallon,
      int mileage) {

    FuelPrice fuelPrice = fuelPriceRepo.findTop1ByFromDateBeforeOrderByFromDateDesc(localDate);

    if (fuelType == FuelType.ULSD) {
      BigDecimal totalFuelPrice = calculateFuelPriceFor1Litre(fuelPrice.getUlsdPrice(),
          fuelPrice.getUlsdDuty(), fuelPrice.getUlsdVat());
    } else {
      BigDecimal totalFuelPrice = calculateFuelPriceFor1Litre(fuelPrice.getUlspPrice(),
          fuelPrice.getUlspDuty(), fuelPrice.getUlspVat());
    }
    return new CalculationResult();

  }

  BigDecimal calculateFuelPriceFor1Litre(BigDecimal price, BigDecimal duty,
      BigDecimal vat) {
    BigDecimal valueBeforeTax = price.add(duty);
    BigDecimal tax = valueBeforeTax.divide(BigDecimal.valueOf(100))
        .multiply(vat);//.setScale(0, Roundin);
    return valueBeforeTax.add(tax);
  }
}
