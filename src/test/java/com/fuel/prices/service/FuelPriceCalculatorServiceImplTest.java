package com.fuel.prices.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class FuelPriceCalculatorServiceImplTest {

  @InjectMocks
  FuelPriceCalculatorServiceImpl fuelPriceCalculatorService;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void testCalculateFuelPriceFor1Litre() {
//    BigDecimal totalPrice = fuelPriceCalculatorService
//        .calculateFuelPriceFor1Litre(BigDecimal.valueOf(56.70), BigDecimal.valueOf(57.95),
//            BigDecimal.valueOf(20));
//    assertEquals(totalPrice, BigDecimal.valueOf(137.58));

  }

}