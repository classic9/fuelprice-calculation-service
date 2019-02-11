package com.fuel.prices.service;

import com.fuel.prices.model.DutyRate;
import com.fuel.prices.model.FuelPrice;
import com.fuel.prices.repo.FuelPriceRepo;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

@Controller
@Slf4j
public class CSVLoader {

  @Value("${csv.path:https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/776070/CSV.csv}")
  private String url;
  @Value("${duty.string.validator:Duty rate per litre \\(.\\) from ([0-9]{1,2} .* [1-9]{1}[0-9]{3})}")
  private String dutyStringValidator;  //validates Strings like: Duty rate per litre (£) from 7 March 2001

  private DateTimeFormatter fuelPriceDateFormatter;
  private DateTimeFormatter dutyRateDateFormatter;

  @Autowired
  private FuelPriceRepo fuelPriceRepo;

  @Autowired
  public CSVLoader(FuelPriceRepo fuelPriceRepo) {
    this.fuelPriceRepo = fuelPriceRepo;

    this.fuelPriceDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    this.dutyRateDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
    this.fuelPriceDateFormatter.withLocale(Locale.ENGLISH);
    this.dutyRateDateFormatter.withLocale(Locale.ENGLISH);

  }

  public void readCSV() throws IOException {
    URL csvPath = new URL(url);
    try (InputStream inputStream = csvPath.openConnection().getInputStream()) {
      try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
        readCSV(inputStreamReader);
      }
    }

  }

  /**
   * Gives the flexibility of reading from any input stream.
   */
  public void readCSV(InputStreamReader inputStreamReader) throws IOException {
    try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      try (CSVReader csvReader = new CSVReader(bufferedReader)) {
        csvReader.forEach(row -> {
          ifRowContainsFuelDataSaveInCache(row);
          if (row[8].matches(dutyStringValidator)) {
            ifRowContainsDutyRateInformationSaveInCache(row);
          }
        });

      }
    }
    log.info("Read and Saved stats: Fuel prices count: '{}'",
        fuelPriceRepo.count());
  }

  private void ifRowContainsDutyRateInformationSaveInCache(String[] row) {
    Pattern pattern = Pattern.compile(dutyStringValidator);
    Matcher matcher = pattern.matcher(row[8]);
    matcher.find();
    try {
      if (matcher.matches()) {
        DutyRate dutyRate = new DutyRate();
        LocalDate localDate = LocalDate.parse(matcher.group(1), dutyRateDateFormatter);
        BigDecimal rate = stringToBigDecimal(13, row);
        dutyRate.setValidFrom(LocalDate.parse(matcher.group(1), dutyRateDateFormatter));
        dutyRate.setRate(rate);
        log.info("localDate: {}, dutyRate: {}", localDate, dutyRate);
      }
    } catch (DateTimeParseException | NumberFormatException exception) {
      log.warn(
          "skipping row, for dutyRate: {}, as the column is non-date, or corresponding values are null or empty, {}",
          Arrays.asList(row), exception.getMessage());
    }
  }

  private void ifRowContainsFuelDataSaveInCache(String[] row) {
    try {
      //assuming row containing date will always have corresponding values. if any missing value found, the below will treat it as bad data and skip the row.
      LocalDate localDate = LocalDate.parse(row[0], fuelPriceDateFormatter);
      FuelPrice fuelPrice = new FuelPrice();
      fuelPrice.setFromDate(localDate);
      fuelPrice.setUlspPrice(stringToBigDecimal(1, row));
      fuelPrice.setUlsdPrice(stringToBigDecimal(2, row));
      fuelPrice.setUlspDuty(stringToBigDecimal(3, row));
      fuelPrice.setUlsdDuty(stringToBigDecimal(4, row));
      fuelPrice.setUlspVat(stringToBigDecimal(5, row));
      fuelPrice.setUlsdVat(stringToBigDecimal(6, row));
      fuelPriceRepo.save(fuelPrice);
      log.info("{}", fuelPrice);
    } catch (DateTimeParseException | NumberFormatException exception) {
      log.warn(
          "skipping row: {}, as the date column is non-date, or corresponding values are null or empty, {}",
          Arrays.asList(row), exception.getMessage());
    }
  }

  private BigDecimal stringToBigDecimal(int i, String[] values) {
    if (!StringUtils.isEmpty(values[i])) {
      return new BigDecimal(values[i]);
    }
    throw new NumberFormatException(
        String.format("Expecting a decimal value but was empty. at position: %d", i));
  }
}
