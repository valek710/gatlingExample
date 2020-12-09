import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutIBANFinHelpRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def randomData: RandomData = new RandomData()

  val putIBAN: ScenarioBuilder = scenario("Put IBAN")
    .exec(
      _.set("iban", randomData.iban())
    )
    .exec(
      baseRequest.putRequest("Put IBAN",
        "/documents/${docId}", token,
        """
          |{
          |"properties":
          | [
          |   {
          |   "path":"main.iban",
          |   "value":"UA${iban}",
          |   "previousValue":"UA"
          |   }
          | ]
          |}
      """.stripMargin
      )
    )

  val putIBAN1: ScenarioBuilder = scenario("Put IBAN")
    .exec(
      _.set("iban", randomData.iban())
    )
    .exec(
      baseRequest.putRequest("Put IBAN",
        "/documents/" + docId, token,
        """
          |{
          |"properties":
          | [
          |   {
          |   "path":"main.iban",
          |   "value":"UA${iban}",
          |   "previousValue":"UA"
          |   }
          | ]
          |}
      """.stripMargin
      )
    )

  setUp(
    putIBAN1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
