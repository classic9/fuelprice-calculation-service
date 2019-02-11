package com.fuel.prices.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DutyRate {

  @Id
  private LocalDate validFrom;
  private BigDecimal rate;
}
