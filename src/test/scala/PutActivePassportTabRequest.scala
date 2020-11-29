import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutActivePassportTabRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def postOnboardRequest: PostOnboardRequest = new PostOnboardRequest()

  val setActivePassportTab: ScenarioBuilder = baseRequest.putRequest("Put active passport tab", "/documents/${docId}", token,
    """
      |{
      |"properties":[
      | {
      |   "path":"pasport",
      |   "value":{
      |     "tabs":{
      |       "active":"passport",
      |       "passport":{
      |         "pasNumber":{},
      |         "date":{}
      |       }
      |     }
      |   }
      | }
      | ]
      |}
      |""".stripMargin
  )
  private val script: ScenarioBuilder = scenario("Scenario")
    .exec(postOnboardRequest.postOnboard)
    .exec(setActivePassportTab)

  setUp(
    script.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
