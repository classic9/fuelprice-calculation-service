**Fuel Price Calculator**

* To run this application you will need gradle. - I have decided to use gradle mainly because of its 
advantage of building things quicker than Maven and its ability to manage code and dependencies in a 
neat & clean way.  

* Once Application is build, you can run this application as any Spring boot application by invoking
the main class in this case, the main class is: FuelPriceCalculationEngine.java  

* As a normal practice I always prefer using Lombok library for automatic code generation for code 
 which no body likes to write (Boiler plate code) :)
 
* For lombok, you will need lombok plugin installed on your IDE and Annotation Processing enabled. 
(Not required if you are running application through gradle bootRun task)

* I have decided to use H2 database to do a quick demo of this, This could potentially be implemented 
using just the cache and then calculating the start working day of a week based on given date and getting
value out of cache. 

* I have decided to use BigDecimal as it is a good practice to use BigDecimals when you calculate prices. 

* I prefer using Cucumber tests with Java as this gives greater flexibility in terms of who will write 
 tests and who can validate this test. 

* I have added basic testing scenario for Acceptance Tests/Integration Tests just to show my ability to write them,
There are many scenarios which can be covered, like what happens when the date is prior to 03/06/2003 etc, 
due to time constrains I am going to skip them.

* As for now I have not spend too much time on Loading the data through a remote CSV, for extensibility reasons A
Spring scheduler could easily be used to refresh data with in the database for automatic reloads which jvm is hot.

 