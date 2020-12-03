import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class MyClosedTasksWithFilterRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getMyTasks: ScenarioBuilder = baseRequest.getRequest("Get my closed tasks with filter",
    "/tasks?filters%5Bfinished%5D=1&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=me&filters%5Bfiltered%5D=false&count=10", token)

  setUp(
    getMyTasks.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
