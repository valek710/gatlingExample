import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class GetWorkflowDataRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getWorkflow: ScenarioBuilder = baseRequest.getRequest("Get workflow",
    "/workflows/${workflowId}", token)

  val getWorkflow1: ScenarioBuilder = baseRequest.getRequest("Get workflow",
    "/workflows/" + workflowId, token)

  setUp(
    getWorkflow1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
