import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class GetTaskRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val getTask: ScenarioBuilder = baseRequest.getRequest("Get onboard task", "/tasks/${taskId}", token)

  val getTask1: ScenarioBuilder = baseRequest.getRequest("Get onboard task", "/tasks/" + taskId, token)

  setUp(
    getOnboardTask1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
