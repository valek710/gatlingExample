import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutWorkplaceAcceptFinHelpRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val putAccept: ScenarioBuilder = baseRequest.putRequest("Put workplace.confirm = 0",
    "/documents/${docId}", token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"workplace.confirm",
      |   "value":["0"],
      |   "previousValue":null
      |   }
      | ]
      |}
      """.stripMargin
  )

  val putAccept1: ScenarioBuilder = baseRequest.putRequest("Put workplace.confirm = 0",
    "/documents/" + docId, token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"workplace.confirm",
      |   "value":["0"],
      |   "previousValue":null
      |   }
      | ]
      |}
      """.stripMargin
  )

  setUp(
    putAccept1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
