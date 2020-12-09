import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PostCommitRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val postCommit: ScenarioBuilder = baseRequest.postRequest("Post commit", "/tasks/${taskId}/commit",
  token, "{}")

  setUp(
    postCommit.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
