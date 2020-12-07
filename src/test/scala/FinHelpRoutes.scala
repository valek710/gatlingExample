import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class FinHelpRoutes extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def getDashboard: GetDashboard = new GetDashboard()
  def postFinHelpClaim: PostFinHelpClaim = new PostFinHelpClaim()
  def finHelpGetStatus: FinHelpGetStatus = new FinHelpGetStatus()

  private val rps1: Integer = rps.toInt / 5 * 2
  private val rps2: Integer = rps.toInt / 5
  private val ses1: Integer = sessions.toInt / 5 * 2
  private val ses2: Integer = sessions.toInt / 5 * 2

  val getDashboard1: ScenarioBuilder = scenario("Get dashboard")
    .exec(
      getDashboard.getDashboard
    )

  val postFinHelp1: ScenarioBuilder = scenario("postFinHelp")
    .exec(
      postFinHelpClaim.postFinHelp
    )

  val getStatus1: ScenarioBuilder = scenario("getStatus")
    .exec(
      finHelpGetStatus.getStatus
    )

  setUp(
    getDashboard1.inject(atOnceUsers(ses1)).throttle(
      reachRps(rps1) in (1 seconds),
      holdFor(1 hour)
    ),
    postFinHelp1.inject(atOnceUsers(ses2)).throttle(
      reachRps(rps2) in (1 seconds),
      holdFor(1 hour)
    ),
    getStatus1.inject(atOnceUsers(ses1)).throttle(
      reachRps(rps1) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
