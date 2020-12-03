import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class GetDraftsRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getDrafts: ScenarioBuilder = scenario("Get drafts")
    .exec(
      http("get drafts")
        .get("/workflows?filters%5Btasks%5D%5Bdeleted%5D=0&filters%5Bis_draft%5D=true&filters%5Bfiltered%5D=false&sort%5Bdocuments%5D%5Bupdated_at%5D=desc&count=10")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .check(status.is(200))
        .check(
          jsonPath("$.data[0].entryTaskId").saveAs("draftId")
        )
        .check(
          jsonPath("$.data[0].workflowTemplateId").saveAs("documentTemplateId")
        )
    )

  setUp(
    getDrafts.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
