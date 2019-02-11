Feature: Now you can calculate prices live by looking up Fuel price database.

  Scenario: When I do a lookup on petrol for prices by passing a date(could be historical), along with mpg and mileage information. I get total value of fuel used.
    Given the path to query fuel cost calculation is "/calculate/fuelprice"
    And mandatory request params "date" is "2017-10-03"
    And mandatory request params "fuelType" is "ULSD"
    And mandatory request params "milesPerGallon" is "25"
    And mandatory request params "mileage" is "100"
    When I make a rest call with the above params and to the above path
    Then I get response code 200
    And response matching:
    """

    """
