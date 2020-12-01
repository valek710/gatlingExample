import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutSerieAndNumberRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val setSerieAndNumber: ScenarioBuilder = baseRequest.putRequest("Put serie and number", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      |   {"path":"pasport.tabs.passport.pasNumber.serie",
      |   "value":"ЕН"
      |   },
      |   {
      |   "path":"pasport.tabs.passport.pasNumber.number",
      |   "value":"123456"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  val setSerieAndNumber1: ScenarioBuilder = baseRequest.putRequest("Put serie and number", "/documents/" + docId, token,
    """
      |{
      | "properties":[
      |   {"path":"pasport.tabs.passport.pasNumber.serie",
      |   "value":"ЕН"
      |   },
      |   {
      |   "path":"pasport.tabs.passport.pasNumber.number",
      |   "value":"123456"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  setUp(
    setSerieAndNumber1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
