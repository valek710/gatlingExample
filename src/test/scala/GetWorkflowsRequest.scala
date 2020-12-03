import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class GetWorkflowsRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest();

  val getWorkflows: ScenarioBuilder = scenario("Get workflows")
    .exec(
      http("get workflows")
        .get("/workflows?filters%5Btasks%5D%5Bdeleted%5D=0&filters%5Bis_draft%5D=false&filters%5Bfiltered%5D=false&sort%5Btasks%5D%5Bfinished_at%5D=desc&count=10")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .check(status.is(200))
        .check(
          jsonPath("$.data[0].id").saveAs("workflowId")
        )
        .check(
          jsonPath("$.data[0].workflowTemplateId").saveAs("workflowTemplateId")
        )
    )

  setUp(
    getWorkflows.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
