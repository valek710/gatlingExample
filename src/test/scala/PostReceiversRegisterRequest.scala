import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PostReceiversRegisterRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val postRecivers: ScenarioBuilder = baseRequest.postRequest("Post receivers",
    "/registers/keys/69/records/filter?strict=true&limit=1000&control=documents.${docId}.applicantInfo.receiversRegister", token, "{}")

  val postRecivers1: ScenarioBuilder = baseRequest.postRequest("Post receivers",
    "/registers/keys/69/records/filter?strict=true&limit=1000&control=documents." + docId + ".applicantInfo.receiversRegister", token, "{}")

  setUp(
    postRecivers1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
