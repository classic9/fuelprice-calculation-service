package com.fuel.prices.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fuel.prices.component.CSVLoader;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

  private final CSVLoader csvLoader;
  private final ObjectMapper objectMapper;

  @Autowired
  public CommonConfig(CSVLoader csvLoader, ObjectMapper objectMapper) {
    this.csvLoader = csvLoader;
    this.objectMapper = objectMapper;
  }

  @PostConstruct
  public void loadCSV() throws IOException {
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.registerModule(new JavaTimeModule());
    csvLoader.readCSV();
  }
}
