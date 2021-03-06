import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutStreetNameOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val setStreetName: ScenarioBuilder = baseRequest.putRequest("Put street name", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      | {
      |   "path":"address.street.streetName",
      |   "value":"Хмельницька"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  val setStreetName1: ScenarioBuilder = baseRequest.putRequest("Put street name", "/documents/" + docId, token,
    """
      |{
      | "properties":[
      | {
      |   "path":"address.street.streetName",
      |   "value":"Хмельницька"
      |   }
      | ]
      |}
      |""".stripMargin
  )


  setUp(
    setStreetName1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
