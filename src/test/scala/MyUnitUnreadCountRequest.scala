import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class MyUnitUnreadCountRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getUnreadCount: ScenarioBuilder = baseRequest.getRequest("Get unit unread tasks count",
    "/tasks/unread/count?filters[finished]=0&filters[deleted]=0&filters[assigned_to]=unit&filters[filtered]=false", token)

  setUp(
    getUnreadCount.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
