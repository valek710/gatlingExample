import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutRegionOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def postOnboardRequest: PostOnboardRequest = new PostOnboardRequest()

  val setRegion: ScenarioBuilder = baseRequest.putRequest("Put region data", "/documents/${docId}", token,
    """
      |{
      | "properties":[
      | {
      |   "path":"address.region",
      |   "value":{
      |     "id":"4464e970-0fb6-11ea-b75b-9375609372d6",
      |     "registerId":164,
      |     "keyId":408,
      |     "stringified":"м.Київ",
      |     "isRelationId":"26",
      |     "atuId":"26",
      |     "atuNameId":"98419",
      |     "isRelationLink":"",
      |     "atuParentId":"",
      |     "level":"1",
      |     "type":"1",
      |     "status":"1",
      |     "code":"8000000000",
      |     "cvk":"",
      |     "name":"м.Київ"
      |     }
      |   }
      | ]
      |}
      |""".stripMargin
  )

  private val script: ScenarioBuilder = scenario("Scenario")
    .exec(postOnboardRequest.postOnboard)
    .exec(setRegion)

  setUp(
    script.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
