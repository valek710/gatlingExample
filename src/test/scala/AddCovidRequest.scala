import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import org.bouncycastle.util.test.TestRandomData

import scala.concurrent.duration.DurationInt

class AddCovidRequest extends BaseSimulation {
  def randomData: RandomData = new RandomData()

  val postCovid: ScenarioBuilder = scenario("Add covid request")
    .exec(
      http("Add covid request")
        .post("https://vaccine.diia.org.ua/wait-list")
        .headers(
          Map(
            "Authorization" -> ("Basic " + covidToken.replaceAll("\"", "")),
            "Content-Type" -> "application/json"
          )
        )
        .body(
          StringBody(
            """
              |{
              |    "birthYear": "1989",
              |    "birthMonth": "05",
              |    "hashId": "${hash}",
              |    "vaccinationPlace": "${ipn}",
              |    "phoneNumber": "380508888888",
              |    "email": "test@test.com",
              |    "firstName": "Artem"
              |}
              |""".stripMargin
          )
        )
        .check(status.is(201))
    )

  val checkCovid: ScenarioBuilder = scenario("Check covid list")
    .exec(
      http("Check covid list")
        .get("https://vaccine.diia.org.ua/wait-list/${hash}")
        .headers(
          Map(
            "Authorization" -> ("Basic " + covidToken.replaceAll("\"", "")),
            "Content-Type" -> "application/json"
          )
        )
        .check(status.is(200))
    )

  val deleteCovid: ScenarioBuilder = scenario("Delete covid request")
    .exec(
      http("Delete covid request")
        .delete("https://vaccine.diia.org.ua/wait-list/${hash}")
        .headers(
          Map(
            "Authorization" -> ("Basic " + covidToken.replaceAll("\"", "")),
            "Content-Type" -> "application/json"
          )
        )
        .check(status.is(204))
    )

  val script: ScenarioBuilder = scenario("COVID simulation")
    .exec(
      _.set("hash", randomData.randomHashForCovid())
        .set("ipn", randomData.randomDigit(1000000000, 9999999999L))
    )
    .exec(postCovid)
    .exec(checkCovid)
    .exec(deleteCovid)


  setUp(
    script.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
