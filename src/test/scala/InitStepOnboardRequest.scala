import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class InitStepOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val initStep: ScenarioBuilder = baseRequest.putRequest("Init step", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      |   {
      |   "path":"address",
      |   "value":
      |     {
      |     "street":{},
      |     "building":{},
      |     "apt":{}
      |     }
      |   }
      | ]
      |}
      |""".stripMargin
  )

  val initStep1: ScenarioBuilder = baseRequest.putRequest("Init step", "/documents/" + docId, token,
    """
      |{
      | "properties":[
      |   {
      |   "path":"address",
      |   "value":
      |     {
      |     "street":{},
      |     "building":{},
      |     "apt":{}
      |     }
      |   }
      | ]
      |}
      |""".stripMargin
  )

  setUp(
    initStep1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
