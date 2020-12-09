import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class GetDocumentRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val getDocument: ScenarioBuilder = baseRequest.getRequest("Get document", "/documents/${docId}", token)

  val getDocument1: ScenarioBuilder = baseRequest.getRequest("Get document", "/documents/" + docId, token)

  setUp(
    getDocument1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
