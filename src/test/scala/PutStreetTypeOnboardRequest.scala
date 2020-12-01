import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutStreetTypeOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val setStreetType: ScenarioBuilder = baseRequest.putRequest("Put street type", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      |   {
      |   "path":"address.street.streetType",
      |   "value":"вулиця"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  val setStreetType1: ScenarioBuilder = baseRequest.putRequest("Put street type", "/documents/" + docId, token,
    """
      |{
      | "properties":[
      |   {
      |   "path":"address.street.streetType",
      |   "value":"вулиця"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  setUp(
    setStreetType1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
