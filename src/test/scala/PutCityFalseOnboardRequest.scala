import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutCityFalseOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val setCityFalse: ScenarioBuilder = baseRequest.putRequest("Put city false", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      | {
      |   "path":"address.city",
      |   "value":false
      |   }
      | ]
      |}
      |""".stripMargin
  )

  val setCityFalse1: ScenarioBuilder = baseRequest.putRequest("Put city false", "/documents/" + docId, token,
    """
      |{
      | "properties":[
      | {
      |   "path":"address.city",
      |   "value":false
      |   }
      | ]
      |}
      |""".stripMargin
  )

  setUp(
    setCityFalse1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
