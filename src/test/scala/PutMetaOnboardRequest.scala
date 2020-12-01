import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutMetaOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val putMetaOnboard: ScenarioBuilder = baseRequest.putRequest("Put meta onboard",
    "/tasks/${taskId}/meta", token,
    """
      |{
      |"meta":{
      |   "isRead":true
      |   }
      | }
      """.stripMargin
  )

  val putMetaOnboard1: ScenarioBuilder = baseRequest.putRequest("Put meta onboard",
    "/tasks/" + onboardId + "/meta", token,
    """
      |{
      |"meta":{
      |   "isRead":true
      |   }
      | }
      """.stripMargin
  )

  setUp(
    putMetaOnboard1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
