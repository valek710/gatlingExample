import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class PostFinHelpClaim extends BaseSimulation {
  def randomData: RandomData = new RandomData()

  val postFinHelp: ScenarioBuilder = scenario("Post fin-help")
    .exec(
      _.set("iban", randomData.iban())
        .set("ipn", randomData.randomDigit(1000000000, 9999999999L))
    )
    .exec(
      http("Post fin-help")
        .post("/diia-mobile-app/fin-help/claim")
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
              |  "workflowTemplateId": "31009",
              |  "taskTemplateId": "31009003",
              |  "initData": {
              |    "iban": "UA${iban}",
              |    "rnokpp": "${ipn}",
              |    "firstName": "Тест",
              |    "middleName": "Тестович",
              |    "lastName": "Тестовий",
              |    "address": "Київська обл., Києво-Святошинський р-н, м. Боярка, 123, кв. 1234"
              |  }
              |}
              |""".stripMargin
          )
        )
        .check(status.not(500), status.not(502))
    )

  setUp(
    postFinHelp.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
