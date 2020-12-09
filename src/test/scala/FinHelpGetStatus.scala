import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class FinHelpGetStatus extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getStatus: ScenarioBuilder = scenario("Get status")
    .exec(
      http("Get status")
        .post("/diia-mobile-app/fin-help/get-status")
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
              |  "id": "125e6e60-3a08-11eb-915e-2b0f9841e5ae"
              |}
              |""".stripMargin
          )
        )
        .check(status.is(200))
    )

  setUp(
    getStatus.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
