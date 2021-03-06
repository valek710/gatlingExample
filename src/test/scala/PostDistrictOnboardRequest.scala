import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PostDistrictOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val filterDistrict: ScenarioBuilder = baseRequest.postRequest("Post empty district",
    "/registers/keys/410/records/filter?strict=true&limit=1000&control=documents.${docId}.address.district", token, "{}")

  val filterDistrict1: ScenarioBuilder = baseRequest.postRequest("Post empty district",
    "/registers/keys/410/records/filter?strict=true&limit=1000&control=documents." + docId + ".address.district", token, "{}")

  setUp(
    filterDistrict1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
