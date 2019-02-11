package com.fuel.prices.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CalculationResult {

  private BigDecimal totalValueToday;
  private FuelPrice fuelPriceToday;
  private BigDecimal totalValueOnDateGiven;
  private FuelPrice fuelPriceOnGivenDate;
  private BigDecimal ChangeInValueWhenComparedToToday;
}
