import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class PostOnboardRequest extends BaseSimulation {
  val postOnboard: ScenarioBuilder = scenario("Post onboard")
    .exec(
      http("post Onboard")
        .post("/tasks")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .body(
          StringBody(
            """
              |{
              | "workflowTemplateId": "3004",
              | "taskTemplateId": "3004001"
              |}""".stripMargin
          )
        )
        .check(status.is(200))
        .check(
          jsonPath("$.data.id").saveAs("taskId")
        )
        .check(
          jsonPath("$.data.documentId").saveAs("docId")
        )
    )

  setUp(
    postOnboard.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
