import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class GetDashboard extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getDashboard: ScenarioBuilder = scenario("Get dashboard")
    .exec(
      http("Get dashboard")
        .post("/diia-mobile-app/fin-help/get-dashboard")
        .headers(
          Map(
            "Authorization" -> "Basic ZGlpYS1zdGFnZTp1R2V6a0RwZzlYb1V2dWFTZnFpc01XQVpTWDFmM3Q0WHBEMWhhdkRZMENUa0g3alhyRGRaNVdlcTdUUGVRVGho",
            "Content-Type" -> "application/json"
          )
        )
        .body(
          StringBody(
            """
              |{
              |    "rnokpp": "3186405475"
              |}
              |""".stripMargin
          )
        )
        .check(status.is(200))
    )

  setUp(
    getDashboard.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
