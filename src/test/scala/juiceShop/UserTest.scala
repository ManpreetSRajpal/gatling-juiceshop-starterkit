package juiceShop

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class UserTest extends Simulation {

  val httpProtocol = http.baseUrl("http://localhost:3000/")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  var idNumbers = (221 to 230).iterator

  val customFeeder = Iterator.continually(Map(
    "email" -> ("\" test"+idNumbers.next()+"@gmail.com \"")
  ))

  def createNewUser() = {

    repeat(idNumbers.length) {
      feed(customFeeder)
        .exec(
          http("Create new Game - ${email}")
            .post("api/Users")
            .body(ElFileBody("src/test/resources/bodies/user.json")).asJson
            .check(jsonPath("$.data.email").saveAs("email"))
        )

    }
  }




  val sce = scenario("Test user login")
    .exec(createNewUser())

  setUp(
    sce.inject(
      atOnceUsers(1)
    ).protocols(
      httpProtocol)
  )

}
