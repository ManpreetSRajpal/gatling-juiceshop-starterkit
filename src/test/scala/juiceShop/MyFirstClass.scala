package juiceShop

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MyFirstClass extends Simulation {

  // 1. HttpConfiguration

  val httpProtocol = http.baseUrl("http://localhost:3000/")
    .acceptHeader("application/json");


  //2. Scenario Definition

  val scn = scenario("Juice shop Product search tet")
    .exec(
      http("Get all products")
      .get("rest/products/search/")
    )

  //3. Load Scenario
  setUp(
    scn.inject(atOnceUsers(5))
  ).protocols(httpProtocol)


}
