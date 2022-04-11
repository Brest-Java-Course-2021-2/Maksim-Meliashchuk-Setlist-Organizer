package simulation

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RestSimulation extends Simulation{

  val httpProtocol = http.baseUrl("https://setlist-organizer-rest.herokuapp.com/")

  val scn = scenario("RestSimulation")
    .exec(http("repertoire").get("repertoire"))
    .pause(3)
    .exec(http("bands").get("bands"))
    .pause(6)
    .exec(http("bands_dto").get("bands_dto"))
    .pause(2)
    .exec(http("tracks_dto").get("tracks_dto"))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

}
