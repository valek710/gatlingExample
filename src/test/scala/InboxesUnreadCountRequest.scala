import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class InboxesUnreadCountRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getUnreadCount: ScenarioBuilder = baseRequest.getRequest("Get /user-inboxes/unread/count",
    "/user-inboxes/unread/count", token)

  setUp(
    getUnreadCount.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
