import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutIssuerRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val setIssuer: ScenarioBuilder = baseRequest.putRequest("Put issuer", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      |   {
      |   "path":"pasport.tabs.passport.pasIssurer",
      |   "value":"Рокитнянським РВ ГУ МВС України в Київській області"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  val setIssuer1: ScenarioBuilder = baseRequest.putRequest("Put issuer", "/documents/" + docId, token,
    """
      |{
      | "properties":[
      |   {
      |   "path":"pasport.tabs.passport.pasIssurer",
      |   "value":"Рокитнянським РВ ГУ МВС України в Київській області"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  setUp(
    setIssuer1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
