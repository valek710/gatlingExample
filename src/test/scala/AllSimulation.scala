import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class AllSimulation extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def onboardSimulation: OnboardSimulation = new OnboardSimulation()
  def cabinetSimulation: CabinetSimulation = new CabinetSimulation()
  def finHelpRoutes: FinHelpRoutes = new FinHelpRoutes()

  setUp(
    onboardSimulation.script.inject(rampUsers(sessions.toInt) during(20 second)).throttle(
      reachRps(rps.toInt / 4) in (20 seconds),
      holdFor(1 hour)
    ),
    cabinetSimulation.script.inject(rampUsers(sessions.toInt) during(20 second)).throttle(
      reachRps(rps.toInt / 4) in (20 seconds),
      holdFor(1 hour)
    ),
    finHelpRoutes.getDashboard1.inject(rampUsers(sessions.toInt) during(20 second)).throttle(
      reachRps(rps.toInt / 4) in (1 seconds),
      holdFor(1 hour)
    ),
    finHelpRoutes.postFinHelp1.inject(rampUsers(sessions.toInt) during(20 second)).throttle(
      reachRps(rps.toInt / 6) in (1 seconds),
      holdFor(1 hour)
    ),
    finHelpRoutes.getStatus1.inject(rampUsers(sessions.toInt) during(20 second)).throttle(
      reachRps(rps.toInt / 4) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
