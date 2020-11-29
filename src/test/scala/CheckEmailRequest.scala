import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class CheckEmailRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val checkEmail: ScenarioBuilder = baseRequest.postRequest("Check email", "/users/email/check", token,
    """
      |{
      |"email":"v.kostromin@kitsoft.kiev.ua"
      |}
      |""".stripMargin
  )

  setUp(
    checkEmail.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
