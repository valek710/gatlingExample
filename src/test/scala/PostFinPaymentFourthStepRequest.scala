import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PostFinPaymentFourthStepRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val postPayment: ScenarioBuilder = baseRequest.postRequest("Post payment fourth step",
    "/registers/keys/71/records/filter?strict=true&limit=1000&control=documents.${docId}.main.paymentsRegister", token, "{}")

  val postPayment1: ScenarioBuilder = baseRequest.postRequest("Post payment fourth step",
    "/registers/keys/71/records/filter?strict=true&limit=1000&control=documents." + docId + ".main.paymentsRegister", token, "{}")

  setUp(
    postPayment1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
