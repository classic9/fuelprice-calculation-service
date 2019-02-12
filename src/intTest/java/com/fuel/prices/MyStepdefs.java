package com.fuel.prices;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MyStepdefs {

  private String path;
  private ResponseEntity<String> response;

  @Autowired
  TestRestTemplate template;

  @Given("the path to query fuel cost calculation is {string}")
  public void thePathToQueryFuelCostCalculationIs(String path) {
    this.path = path+"?";
  }

  @And("mandatory request params {string} is {string}")
  public void mandatoryRequestParamsIs(String key, String value) {
    path = String.format("%s&%s=%s", path, key, value);
  }

  @When("I make a rest call with the above params and to the above path")
  public void iMakeARestCallWithTheAboveParamsAndToTheAbovePath() {
    response = template.getForEntity(path, String.class);
  }

  @Then("I get response code {int}")
  public void iGetResponseCode(int statusCode) {
    HttpStatus currentStatusCode = response.getStatusCode();
    assertThat("status code is incorrect : " + response.getBody(), currentStatusCode.value(),
        is(statusCode));
  }

  @And("response matching:")
  public void responseMatching(String responseString) throws JSONException {

    JSONAssert.assertEquals(response.getBody(), responseString, JSONCompareMode.LENIENT);
  }
}
