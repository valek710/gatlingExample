import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class GetDocumentTemplateByIdRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getDraft: ScenarioBuilder = baseRequest.getRequest("Get document-template",
    "/document-templates/3006", token)

  val getDraft1: ScenarioBuilder = baseRequest.getRequest("Get document-template",
    "/document-templates/" + documentTemplateId, token)

  setUp(
    getDraft1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
