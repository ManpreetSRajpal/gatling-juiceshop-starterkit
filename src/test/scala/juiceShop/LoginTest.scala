package juiceShop

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoginTest extends Simulation {

  val httpProtocol = http.baseUrl("http://localhost:3000/")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  def userLogin() = {
    exec(
      http("User Login")
        .post("rest/user/login/")
        .body(StringBody("{    \"email\": \"test1@test.com\",\n    \"password\": \"password\"\n}"))
        .check(jsonPath("$.authentication.token").saveAs("TOKEN"))
    )
  }

  val sce = scenario("Test user login")
    .exec(userLogin())
    .exec(session => {
      println("Printing the JWT TOKEN")
      println(session("TOKEN").as[String])
      session
    })

  setUp(
    sce.inject(atOnceUsers(5)).protocols(
      httpProtocol)
  )

}
