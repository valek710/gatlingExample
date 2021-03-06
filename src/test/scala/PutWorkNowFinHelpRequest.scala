import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutWorkNowFinHelpRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val putWorkNow: ScenarioBuilder = baseRequest.putRequest("Put work now = 1",
    "/documents/${docId}", token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"workplace.workNow",
      |   "value":"1",
      |   "previousValue":null
      |   }
      | ]
      |}
      """.stripMargin
  )

  val putWorkNow1: ScenarioBuilder = baseRequest.putRequest("Put work now = 1",
    "/documents/" + docId, token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"workplace.workNow",
      |   "value":"1",
      |   "previousValue":null
      |   }
      | ]
      |}
      """.stripMargin
  )

  setUp(
    putWorkNow1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
