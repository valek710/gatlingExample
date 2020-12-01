import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PostRegionOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val filterRegion: ScenarioBuilder = baseRequest.postRequest("Post region",
    "/registers/keys/408/records/filter?strict=true&limit=1000&control=documents.${docId}.address.region", token, "{}")

  val filterRegion1: ScenarioBuilder = baseRequest.postRequest("Post region",
    "/registers/keys/408/records/filter?strict=true&limit=1000&control=documents." + docId + ".address.region", token, "{}")


  setUp(
    filterRegion1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
