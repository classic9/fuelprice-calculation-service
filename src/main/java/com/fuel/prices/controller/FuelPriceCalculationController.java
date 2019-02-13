package com.fuel.prices.controller;

import com.fuel.prices.model.CalculationResult;
import com.fuel.prices.model.FuelType;
import com.fuel.prices.model.ResponseTemplate;
import com.fuel.prices.service.FuelPriceCalculatorService;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FuelPriceCalculationController {

    private FuelPriceCalculatorService fuelPriceCalculatorService;

    @Autowired
    public FuelPriceCalculationController(
            FuelPriceCalculatorService fuelPriceCalculatorService) {
        this.fuelPriceCalculatorService = fuelPriceCalculatorService;
    }

    @GetMapping("/calculate/fuelprice")
    public ResponseTemplate<CalculationResult> calculateFuelPrice(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("date") LocalDate date,
            @RequestParam("fuelType") FuelType fuelType,
            @RequestParam("milesPerGallon") int milesPerGallon,
            @RequestParam("mileage") int mileage) {

        ResponseTemplate<CalculationResult> result = new ResponseTemplate<>();
        CalculationResult calculationResult = fuelPriceCalculatorService.calculate(date, fuelType, milesPerGallon, mileage);
        if(calculationResult.getFuelPriceOnGivenDate()==null){
            result.setError("No data found for a given date.");
        }
        result.setData(calculationResult);
        return result;
    }

}
