import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class GetTemplateRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getTemplate: ScenarioBuilder = baseRequest.getRequest("Get workflow template",
    "/workflow-templates/${documentTemplateId}", token)

  val getTemplate1: ScenarioBuilder = baseRequest.getRequest("Get workflow template",
    "/workflow-templates/" + workflowTemplateId, token)

  setUp(
    getTemplate1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
