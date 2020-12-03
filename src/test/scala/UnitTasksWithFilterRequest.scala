import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class UnitTasksWithFilterRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getUnitTasks: ScenarioBuilder = baseRequest.getRequest("Get unit tasks with filter",
    "/tasks?filters%5Bfinished%5D=0&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=unit&filters%5Bfiltered%5D=false&count=10", token)

  setUp(
    getUnitTasks.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
