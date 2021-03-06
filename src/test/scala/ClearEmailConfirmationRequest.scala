import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class ClearEmailConfirmationRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val clearEmailConfirmation1: ScenarioBuilder = baseRequest.putRequest("Clear email confirmation", "/documents/" + docId, token,
    """
      |{
      |"properties":[
      | {
      | "path":"emailConfirmation.confirmation",
      | "previousValue":null
      | }
      | ]
      |}
      |""".stripMargin
  )

  val clearEmailConfirmation: ScenarioBuilder = baseRequest.putRequest("Clear email confirmation", "/documents/${docId}", token,
    """
      |{
      |"properties":[
      | {
      | "path":"emailConfirmation.confirmation",
      | "previousValue":null
      | }
      | ]
      |}
      |""".stripMargin
  )

  setUp(
    clearEmailConfirmation1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
