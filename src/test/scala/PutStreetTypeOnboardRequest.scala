import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutStreetTypeOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def postOnboardRequest: PostOnboardRequest = new PostOnboardRequest()

  val setStreetType: ScenarioBuilder = baseRequest.putRequest("Put street type", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      |   {
      |   "path":"address.street.streetType",
      |   "value":"вулиця"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  private val script: ScenarioBuilder = scenario("Scenario")
    .exec(postOnboardRequest.postOnboard)
    .exec(setStreetType)

  setUp(
    script.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
