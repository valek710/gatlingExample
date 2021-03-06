import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PostCityOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val filterCity: ScenarioBuilder = baseRequest.postRequest("Post empty city",
    "/registers/keys/411/records/filter?strict=true&limit=1000&control=documents.${docId}.address.city", token, "{}")

  val filterCity1: ScenarioBuilder = baseRequest.postRequest("Post empty city",
    "/registers/keys/411/records/filter?strict=true&limit=1000&control=documents." + docId + ".address.city", token, "{}")

  setUp(
    filterCity1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
