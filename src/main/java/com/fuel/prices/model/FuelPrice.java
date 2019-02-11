package com.fuel.prices.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class FuelPrice {

  @Id
  private LocalDate fromDate;
  private BigDecimal ulspPrice;
  private BigDecimal ulsdPrice;
  private BigDecimal ulspDuty;
  private BigDecimal ulsdDuty;
  private BigDecimal ulspVat;
  private BigDecimal ulsdVat;
}
