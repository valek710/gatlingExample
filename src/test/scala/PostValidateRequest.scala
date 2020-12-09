import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class PostValidateRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val postValidate: ScenarioBuilder = baseRequest.postRequest("Post validate", "/documents/${docId}/validate",
  token, "{}")

  setUp(
    postValidate.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
