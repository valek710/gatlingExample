import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class GetMessagesRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getMessages: ScenarioBuilder = baseRequest.getRequest("Get messages",
    "/messages?filters%5Bdeleted%5D=0&filters%5Bfiltered%5D=false&page=0&count=10&start=0", token)

  setUp(
    getMessages.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
