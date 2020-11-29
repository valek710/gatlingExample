import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutIssuerDateRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def postOnboardRequest: PostOnboardRequest = new PostOnboardRequest()

  val setIssuerDate: ScenarioBuilder = baseRequest.putRequest("Put issuerDate", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      |   {"path":"pasport.tabs.passport.pasNumber.serie",
      |   "value":"ЕН"
      |   },
      |   {
      |   "path":"pasport.tabs.passport.pasNumber.number",
      |   "value":"123456"
      |   }
      | ]
      |}
      |""".stripMargin
  )

  private val script: ScenarioBuilder = scenario("Scenario")
    .exec(postOnboardRequest.postOnboard)
    .exec(setIssuerDate)

  setUp(
    script.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
