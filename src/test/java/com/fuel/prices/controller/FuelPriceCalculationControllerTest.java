package com.fuel.prices.controller;

import com.fuel.prices.model.CalculationResult;
import com.fuel.prices.model.FuelType;
import com.fuel.prices.model.ResponseTemplate;
import com.fuel.prices.service.FuelPriceCalculatorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FuelPriceCalculationControllerTest {


    @Mock
    private FuelPriceCalculatorService fuelPriceCalculatorService;

    @InjectMocks
    private FuelPriceCalculationController fuelPriceCalculationController;


    @Before
    public void setUp() {
        initMocks(this);
    }


    @Test //
    public void testControlerToServiceInvocation(){
        CalculationResult result = new CalculationResult();
        when(fuelPriceCalculatorService.calculate(LocalDate.now(),FuelType.ULSD,90,90)).thenReturn(result);
        ResponseTemplate<CalculationResult> resultResponseTemplate = fuelPriceCalculationController.calculateFuelPrice(LocalDate.now(), FuelType.ULSD,90,90);
        assertEquals(resultResponseTemplate.getData(),result);
        verify(fuelPriceCalculatorService,times(1)).calculate(LocalDate.now(),FuelType.ULSD,90,90);
    }

}