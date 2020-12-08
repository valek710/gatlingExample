import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutEmptyWorkplaceFinHelpRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val putWorkplace: ScenarioBuilder = baseRequest.putRequest("Put empty workplace",
    "/documents/${docId}", token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"workplace",
      |   "value":{},
      |   "previousValue":null
      |   }
      | ]
      |}
      """.stripMargin
  )

  val putWorkplace1: ScenarioBuilder = baseRequest.putRequest("Put empty workplace",
    "/documents/" + docId, token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"workplace",
      |   "value":{},
      |   "previousValue":null
      |   }
      | ]
      |}
      """.stripMargin
  )

  setUp(
    putWorkplace1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
