import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class CheckPhoneRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val checkPhone: ScenarioBuilder = baseRequest.getRequest("Check phone", "/users/phone/already_used?phone=380639480589", token)

  setUp(
    checkPhone.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
