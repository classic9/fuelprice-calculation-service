package com.fuel.prices.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CalculationResult
{
    private BigDecimal totalValueToday;
    private BigDecimal totalValueOnDateGiven;
    private BigDecimal ChangeInValueWhenComparedToToday;

}
