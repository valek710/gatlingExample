import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class PostFinHelpRequest extends BaseSimulation {
  val postTask: ScenarioBuilder = scenario("Post fin help task")
    .exec(
      http("Post fin help task")
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
              | "workflowTemplateId":"31009",
              | "taskTemplateId":"31009001"
              |}
              |""".stripMargin
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
    postTask.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
