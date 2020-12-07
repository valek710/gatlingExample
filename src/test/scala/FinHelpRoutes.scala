import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class FinHelpRoutes extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def getDashboard: GetDashboard = new GetDashboard()
  def postFinHelpClaim: PostFinHelpClaim = new PostFinHelpClaim()
  def finHelpGetStatus: FinHelpGetStatus = new FinHelpGetStatus()

  private val script: ScenarioBuilder = scenario("Scenario")
    .exec(getDashboard.getDashboard)
    .exec(postFinHelpClaim.postFinHelp)
    .exec(finHelpGetStatus.getStatus)

  setUp(
    script.inject(rampUsers(sessions.toInt) during(20 second)).throttle(
      reachRps(rps.toInt) in (20 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
}
