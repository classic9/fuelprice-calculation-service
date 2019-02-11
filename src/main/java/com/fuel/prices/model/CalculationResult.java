package com.fuel.prices.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculationResult {

  private BigDecimal totalValueToday;
  private FuelPrice fuelPriceToday;
  private BigDecimal totalValueOnDateGiven;
  private FuelPrice fuelPriceOnGivenDate;
  private BigDecimal changeInValueWhenComparedToToday;
}
