import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class PutIBANDefaultFinHelpRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val putIBAN: ScenarioBuilder = baseRequest.putRequest("Put IBAN = UA",
    "/documents/${docId}", token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"main.iban",
      |   "value":"UA",
      |   "previousValue":null
      |   }
      | ]
      |}
      """.stripMargin
  )

  val putIBAN1: ScenarioBuilder = baseRequest.putRequest("Put IBAN = UA",
    "/documents/" + docId, token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"main.iban",
      |   "value":"UA",
      |   "previousValue":null
      |   }
      | ]
      |}
      """.stripMargin
  )

  setUp(
    putIBAN1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
