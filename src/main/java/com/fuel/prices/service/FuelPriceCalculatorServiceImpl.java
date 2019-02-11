package com.fuel.prices.service;

import com.fuel.prices.model.CalculationResult;
import com.fuel.prices.model.FuelPrice;
import com.fuel.prices.model.FuelType;
import com.fuel.prices.repo.FuelPriceRepo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuelPriceCalculatorServiceImpl implements FuelPriceCalculatorService {

  private static final double GALLONS_LITERS = 4.546092;
  private final FuelPriceRepo fuelPriceRepo;

  @Autowired
  public FuelPriceCalculatorServiceImpl(FuelPriceRepo fuelPriceRepo) {
    this.fuelPriceRepo = fuelPriceRepo;
  }

  @Override
  public CalculationResult calculate(LocalDate localDate, FuelType fuelType, int milesPerGallon,
      int mileage) {

    FuelPrice fuelPriceGivenDate = fuelPriceRepo
        .findTop1ByFromDateBeforeOrderByFromDateDesc(localDate);
    FuelPrice fuelPriceToday = fuelPriceRepo
        .findTop1ByFromDateBeforeOrderByFromDateDesc(LocalDate.now());
    BigDecimal totalPriceForAGivenDate = getTotalFuelCostForAGivenDate(fuelType,
        milesPerGallon, mileage, fuelPriceGivenDate);
    BigDecimal totalPriceForToday = getTotalFuelCostForAGivenDate(fuelType,
        milesPerGallon, mileage, fuelPriceToday);

    CalculationResult calculationResult = new CalculationResult();
    calculationResult.setTotalValueOnDateGiven(totalPriceForAGivenDate);
    calculationResult.setFuelPriceOnGivenDate(fuelPriceGivenDate);
    calculationResult.setTotalValueToday(totalPriceForToday);
    calculationResult.setFuelPriceToday(fuelPriceToday);
    calculationResult
        .setChangeInValueWhenComparedToToday(totalPriceForToday.subtract(totalPriceForAGivenDate));

    return calculationResult;
  }

  private BigDecimal getTotalFuelCostForAGivenDate(FuelType fuelType,
      int milesPerGallon, int mileage, FuelPrice fuelPrice) {

    BigDecimal totalFuelPricePerLiter;
    if (fuelType == FuelType.ULSD) {
      totalFuelPricePerLiter = calculateFuelPriceFor1Litre(fuelPrice.getUlsdPrice(),
          fuelPrice.getUlsdDuty(), fuelPrice.getUlsdVat());
    } else {
      totalFuelPricePerLiter = calculateFuelPriceFor1Litre(fuelPrice.getUlspPrice(),
          fuelPrice.getUlspDuty(), fuelPrice.getUlspVat());
    }

    BigDecimal totalGallonsUsed = BigDecimal.valueOf(mileage)
        .divide(BigDecimal.valueOf(milesPerGallon));

    BigDecimal totalLitersUsed = BigDecimal.valueOf(GALLONS_LITERS)
        .multiply(totalGallonsUsed);

    return totalLitersUsed.multiply(totalFuelPricePerLiter);
  }

  BigDecimal calculateFuelPriceFor1Litre(BigDecimal price, BigDecimal duty, BigDecimal vat) {
    BigDecimal valueBeforeTax = price.add(duty);
    BigDecimal taxPercentageValue = valueBeforeTax.multiply(BigDecimal.valueOf(0.01)).setScale(4,
        RoundingMode.UP);
    BigDecimal tax = taxPercentageValue.multiply(vat)
        .setScale(2, RoundingMode.UP); //Rounding up to two decimal places.
    return valueBeforeTax.add(tax);
  }
}
