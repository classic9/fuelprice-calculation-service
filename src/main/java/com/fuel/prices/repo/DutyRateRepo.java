package com.fuel.prices.repo;

import com.fuel.prices.model.DutyRate;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DutyRateRepo extends JpaRepository<DutyRate, LocalDate> {

  DutyRate findTop1ByValidFromBeforeOrderByFromDateDesc(LocalDate date);
}
