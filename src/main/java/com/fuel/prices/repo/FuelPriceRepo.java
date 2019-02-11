package com.fuel.prices.repo;

import com.fuel.prices.model.FuelPrice;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelPriceRepo extends JpaRepository<FuelPrice, LocalDate> {

  FuelPrice findTop1ByFromDateBeforeOrderByFromDateDesc(LocalDate date);
}
