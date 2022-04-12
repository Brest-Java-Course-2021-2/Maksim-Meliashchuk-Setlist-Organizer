package simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class RestSimulationLocal extends Simulation{

  val httpProtocol = http.baseUrl("http://localhost:8088/")

  def getAllRepertoire() = {
    repeat(3) {
      exec(http("getAllRepertoire")
        .get("repertoire")
        .check(status.is(200)))
    }
  }

  def getTrack() = {
    repeat(10) {
      exec(http("getTrack")
        .get("repertoire/1")
        .check(status.is(200)))
      exec(http("getTrack")
        .get("repertoire/2")
        .check(status.is(200)))
    }
  }

  val scnRepertoire = scenario("get repertoire")
    .exec(getAllRepertoire())
    .pause(1)
    .exec(getTrack())

  setUp(scnRepertoire.inject(
    incrementUsersPerSec(5)
      .times(5)
      .eachLevelLasting(5 seconds)
      .separatedByRampsLasting(5 seconds)
      .startingFrom(10)
  )).protocols(httpProtocol)
    .assertions(global.successfulRequests.percent.is(100))
}
