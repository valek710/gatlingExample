import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PostEBabyAdminRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def postOnboardRequest: PostOnboardRequest = new PostOnboardRequest()

  val postEBabyAdmin: ScenarioBuilder = baseRequest.postRequest("Post eBaby admin",
    "/registers/keys/427/records/filter?strict=true&limit=1000&control=documents.${docId}.applicant.eBabyAdminRegistry", token, "{}")

  private val script: ScenarioBuilder = scenario("Scenario")
    .exec(postOnboardRequest.postOnboard)
    .exec(postEBabyAdmin)

  setUp(
    script.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
