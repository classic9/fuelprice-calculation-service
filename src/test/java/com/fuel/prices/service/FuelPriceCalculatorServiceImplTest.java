package com.fuel.prices.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.fuel.prices.model.CalculationResult;
import com.fuel.prices.model.FuelPrice;
import com.fuel.prices.model.FuelType;
import com.fuel.prices.repo.FuelPriceRepo;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class FuelPriceCalculatorServiceImplTest {

  @InjectMocks
  private FuelPriceCalculatorServiceImpl fuelPriceCalculatorService;

  @Mock
  private FuelPriceRepo fuelPriceRepo;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void testCalculateFuelPriceFor1Litre() {
    BigDecimal totalPrice = fuelPriceCalculatorService
        .calculateFuelPriceFor1Litre(BigDecimal.valueOf(56.70), BigDecimal.valueOf(57.95),
            BigDecimal.valueOf(20));
    assertEquals(BigDecimal.valueOf(137.58), totalPrice);
  }

  @Test
  public void testCalculate() {

    FuelPrice fuelPriceNow = new FuelPrice();
    fuelPriceNow.setFromDate(LocalDate.parse("2019-02-10"));
    fuelPriceNow.setUlsdPrice(BigDecimal.valueOf(10.1));
    fuelPriceNow.setUlspPrice(BigDecimal.valueOf(11.1));
    fuelPriceNow.setUlsdDuty(BigDecimal.valueOf(20.1));
    fuelPriceNow.setUlsdDuty(BigDecimal.valueOf(20.7));
    fuelPriceNow.setUlsdVat(BigDecimal.valueOf(20.0));
    fuelPriceNow.setUlsdVat(BigDecimal.valueOf(20.0));
    when(fuelPriceRepo.findTop1ByFromDateBeforeOrderByFromDateDesc(fuelPriceNow.getFromDate()))
        .thenReturn(fuelPriceNow);
    when(fuelPriceRepo.findTop1ByFromDateBeforeOrderByFromDateDesc(LocalDate.now()))
        .thenReturn(fuelPriceNow);
    CalculationResult calculationResult = fuelPriceCalculatorService
        .calculate(LocalDate.parse("2019-02-10"), FuelType.ULSD, 30, 300);
    assertNotEquals(null, calculationResult);
    assertEquals(new BigDecimal("1680.23560320"), calculationResult.getTotalValueOnDateGiven());
    assertEquals(new BigDecimal("1680.23560320"), calculationResult.getTotalValueToday());
  }
}