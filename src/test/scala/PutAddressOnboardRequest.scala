import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutAddressOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def postOnboardRequest: PostOnboardRequest = new PostOnboardRequest()

  val setAddress: ScenarioBuilder = baseRequest.putRequest("Put address", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      |   {
      |     "path":"address.building.building",
      |     "value":"10"
      |   },
      |   {
      |     "path":"address.building.index",
      |     "value":"12345"
      |   },
      |   {
      |     "path":"address.apt.index",
      |     "value":"12345"
      |   },
      |   {
      |     "path":"address.isPrivateHouse",
      |     "value":["приватний будинок"]
      |   }
      | ]
      |}
      |""".stripMargin
  )

  private val script: ScenarioBuilder = scenario("Scenario")
    .exec(postOnboardRequest.postOnboard)
    .exec(setAddress)

  setUp(
    script.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
