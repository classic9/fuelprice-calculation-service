package com.fuel.prices;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(features = {"src/intTest/resources/features"}, plugin = {"pretty", "html:build/reports/cucumber/html"})
@RunWith(Cucumber.class)
public class FuelPriceCalculationIT {
}
