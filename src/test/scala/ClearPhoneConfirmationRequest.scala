import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class ClearPhoneConfirmationRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val clearPhoneConfirmation1: ScenarioBuilder = baseRequest.putRequest("Clear phone confirmation", "/documents/" + docId, token,
    """
      |{
      |"properties":[
      | {
      | "path":"phoneConfirmation.confirmation",
      | "previousValue":null
      | }
      | ]
      |}
      |""".stripMargin
  )

  val clearPhoneConfirmation: ScenarioBuilder = baseRequest.putRequest("Clear phone confirmation", "/documents/${docId}", token,
    """
      |{
      |"properties":[
      | {
      | "path":"phoneConfirmation.confirmation",
      | "previousValue":null
      | }
      | ]
      |}
      |""".stripMargin
  )

  setUp(
    clearPhoneConfirmation1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
