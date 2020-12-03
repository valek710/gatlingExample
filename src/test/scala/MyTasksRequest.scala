import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class MyTasksRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getMyTasks: ScenarioBuilder = baseRequest.getRequest("Get my tasks",
    "/tasks?count=10", token)

  setUp(
    getMyTasks.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
