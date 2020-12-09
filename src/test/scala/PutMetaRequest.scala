import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutMetaRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val putMeta: ScenarioBuilder = baseRequest.putRequest("Put meta",
    "/tasks/${taskId}/meta", token,
    """
      |{
      |"meta":{
      |   "isRead":true
      |   }
      | }
      """.stripMargin
  )

  val putMeta1: ScenarioBuilder = baseRequest.putRequest("Put meta",
    "/tasks/" + taskId + "/meta", token,
    """
      |{
      |"meta":{
      |   "isRead":true
      |   }
      | }
      """.stripMargin
  )

  setUp(
    putMeta1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
