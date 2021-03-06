package com.fuel.prices;

import com.fuel.prices.config.CommonConfig;
import cucumber.api.java.Before;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
    FuelPriceCalculationEngine.class, CommonConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class CucumberRoot {

  @Autowired
  protected TestRestTemplate template;

  @Before
  public void before() {

    template.getRestTemplate()
        .setInterceptors(Collections.singletonList((request, body, execution) -> {
          request.getHeaders()
              .add("iv-user", "user");
          return execution.execute(request, body);
        }));
  }
}
