import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class GetDashboard extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def randomData: RandomData = new RandomData()


  val getDashboard: ScenarioBuilder = scenario("Get dashboard")
    .exec(
      _.set("ipn", randomData.randomDigit(1000000000, 9999999999L))
    )
    .exec(
      http("Get dashboard")
        .post("/diia-mobile-app/fin-help/get-dashboard")
        .headers(
          Map(
            "Authorization" -> finHelpToken.replaceAll("\"", ""),
            "Content-Type" -> "application/json"
          )
        )
        .body(
          StringBody(
            """
              |{
              |    "rnokpp": "${ipn}"
              |}
              |""".stripMargin
          )
        )
        .check(status.not(500))
    )

  setUp(
    getDashboard.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
