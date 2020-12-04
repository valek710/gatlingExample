import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

protected class BaseSimulation extends Simulation {

  //variables for tests
  protected val token: String = System.getProperty("TOKEN")
  protected val url: String = System.getProperty("URL")
  protected val idUrl: String = System.getProperty("ID_URL")
  protected val rps: String = System.getProperty("RPS")
  protected val sessions: String = System.getProperty("SESSIONS")
  protected val onboardId: String = System.getProperty("ONBOARD_ID")
  protected val docId: String = System.getProperty("DOC_ID")
  protected val workflowId: String = System.getProperty("WORKFLOW_ID")
  protected val workflowTemplateId: String = System.getProperty("WORKFLOW_TEMPLATE_ID")
  protected val draftId: String = System.getProperty("DRAFT_ID")
  protected val documentTemplateId: String = System.getProperty("DOCUMENT_TEMPLATE_ID")

  /***
   * http configuration
   * baseUrl(url) - set base url, used in requests when not use https://
   * acceptHeader - accepted response headers
   * acceptLanguageHeader - optional
   * acceptEncodingHeader - optional
   * userAgentHeader - firewall can block requests without userAgent
   */
  protected val httpConf: HttpProtocolBuilder = http
    .baseUrl(url)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  /*private val userGetWorkflows10: ScenarioBuilder = scenario("User get workflows with pagination 10")
    .exec(
      http("get /workflows with pagination 10")
        .get("/workflows?filters%5Btasks.deleted%5D=0&filters%5Bis_draft%5D=false&filters%5Bfiltered%5D=false&sort%5Btasks%5D%5Bfinished_at%5D=desc&count=10")
        .header("token", token)
        .check(status.is(200))
    )

  private val userGetWorkflows50: ScenarioBuilder = scenario("User get workflows with pagination 50")
    .exec(
      http("get /workflows with pagination 50")
        .get("/workflows?filters%5Btasks.deleted%5D=0&filters%5Bis_draft%5D=false&filters%5Bfiltered%5D=false&sort%5Btasks%5D%5Bfinished_at%5D=desc&count=50")
        .header("token", token)
        .check(status.is(200))
    )

  private val userGetDrafts10: ScenarioBuilder = scenario("User get drafts with pagination 10")
    .exec(
      http("get drafts with pagination 10")
        .get("/workflows?filters%5Btasks%5D%5Bdeleted%5D=0&filters%5Bis_draft%5D=true&filters%5Bfiltered%5D=false&sort%5Bdocuments%5D%5Bupdated_at%5D=desc&count=10")
        .header("token", token)
        .check(status.is(200))
    )

  private val userGetDrafts50: ScenarioBuilder = scenario("User get drafts with pagination 50")
    .exec(
      http("get drafts with pagination 50")
        .get("/workflows?filters%5Btasks%5D%5Bdeleted%5D=0&filters%5Bis_draft%5D=true&filters%5Bfiltered%5D=false&sort%5Bdocuments%5D%5Bupdated_at%5D=desc&count=50")
        .header("token", token)
        .check(status.is(200))
    )

  private val userGetRegisters: ScenarioBuilder = scenario("User get registers key 15")
    .exec(
      http("get /registers/keys/15/records")
        .get("/registers/keys/15/records?offset=0&limit=615")
        .header("token", token)
        .check(status.is(200))
    )

  private val userStartWorkflow: ScenarioBuilder = scenario("User start random workflow")
      .exec(
        http("get /workflow-templates")
          .get("/workflow-templates")
          .headers(
            Map(
              "token" -> token,
              "Content-Type" -> "application/json"
            )
          )
          .check(status.is(200))
          .check(
            jsonPath("$.data[*].id").saveAs("flowId")
          )
          .check(
            jsonPath("$.data[*].entryTaskTemplateIds[*].id").saveAs("taskId")
          )
      )
      .exec(
        http("post /tasks")
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
                | "workflowTemplateId": "${flowId}",
                | "taskTemplateId": "${taskId}"
                |}""".stripMargin
            )
          )
          .check(status.is(200))
      )*/

  /*val test: ScenarioBuilder = scenario("test")
    .exec(userAuth).pause(1).exec(userCheckAuthInfo)

  setUp(
    userAuth.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (1 seconds),
        holdFor(2 minute),
        jumpToRps(2),
        holdFor(1 minute)
    ),
    userGetServerList.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (2 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userCheckAuthInfo.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (3 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetUnits.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (4 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetInboxesUnread.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (5 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetUnreadMess.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (4 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetMessages.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (3 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetWorkflows10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (2 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetDrafts10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (2 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetUnitTasks10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (4 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetArchivedUnitTasks10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (4 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetTasks10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (2 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    ),
    userGetArchivedTasks10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (2 seconds),
      holdFor(2 minute),
      jumpToRps(2),
      holdFor(1 minute)
    )
  ).protocols(httpConf).maxDuration(10 minutes)
    .assertions(global.successfulRequests.percent.gt(95))
    .assertions(details("get id.../auth").responseTime.max.lt(1000))
   */
}
