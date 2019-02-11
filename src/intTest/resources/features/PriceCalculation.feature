Feature: Now you can calculate prices live by looking up Fuel price database.

  Scenario: When I do a lookup on desiel for prices by passing a date(could be historical), along with mpg and mileage information. I get total value of fuel used.
    Given the path to query fuel cost calculation is "/calculate/fuelprice"
    And mandatory request params "date" is "2017-10-03"
    And mandatory request params "fuelType" is "ULSD"
    And mandatory request params "milesPerGallon" is "25"
    And mandatory request params "mileage" is "100"
    When I make a rest call with the above params and to the above path
    Then I get response code 200
    And response matching:
    """
    {
      "data": {
        "totalValueToday": 4082.39061600,
        "fuelPriceToday": {
          "fromDate": "2019-02-04",
          "ulspPrice": 119.13,
          "ulsdPrice": 129.13,
          "ulspDuty": 57.95,
          "ulsdDuty": 57.95,
          "ulspVat": 20.00,
          "ulsdVat": 20.00
        },
        "totalValueOnDateGiven": 3891.27290832,
        "fuelPriceOnGivenDate": {
          "fromDate": "2017-10-02",
          "ulspPrice": 118.13,
          "ulsdPrice": 120.37,
          "ulspDuty": 57.95,
          "ulsdDuty": 57.95,
          "ulspVat": 20.00,
          "ulsdVat": 20.00
        },
        "changeInValueWhenComparedToToday": 191.11770768
      },
      "error": null
    }
    """

  Scenario: When I do a lookup on petrol for prices by passing a date(could be historical), along with mpg and mileage information. I get total value of fuel used.
    Given the path to query fuel cost calculation is "/calculate/fuelprice"
    And mandatory request params "date" is "2017-10-03"
    And mandatory request params "fuelType" is "ULSP"
    And mandatory request params "milesPerGallon" is "25"
    And mandatory request params "mileage" is "100"
    When I make a rest call with the above params and to the above path
    Then I get response code 200
    And response matching:
    """
    {
      "data": {
        "totalValueToday": 3864.1782,
        "fuelPriceToday": {
          "fromDate": "2019-02-04",
          "ulspPrice": 119.13,
          "ulsdPrice": 129.13,
          "ulspDuty": 57.95,
          "ulsdDuty": 57.95,
          "ulspVat": 20.00,
          "ulsdVat": 20.00
        },
        "totalValueOnDateGiven": 3842.3569584,
        "fuelPriceOnGivenDate": {
          "fromDate": "2017-10-02",
          "ulspPrice": 118.13,
          "ulsdPrice": 120.37,
          "ulspDuty": 57.95,
          "ulsdDuty": 57.95,
          "ulspVat": 20.00,
          "ulsdVat": 20.00
        },
        "changeInValueWhenComparedToToday": 21.8212416
      },
      "error": null
    }
    """
