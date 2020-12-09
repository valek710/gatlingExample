import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class GetPDFRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val getPDF: ScenarioBuilder = baseRequest.getRequest("Get PDF", "/documents/${docId}/pdf", token)

  setUp(
    getPDF.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
