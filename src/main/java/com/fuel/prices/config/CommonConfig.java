package com.fuel.prices.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fuel.prices.service.CSVLoader;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

  @Autowired
  private CSVLoader csvLoader;

  @PostConstruct
  public void loadCSV() throws IOException {
    csvLoader.readCSV();
  }


  @Bean
  public Void updateObjectMapper(ObjectMapper objectMapper) {
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return null;
  }

}
