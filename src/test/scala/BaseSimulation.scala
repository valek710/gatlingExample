import io.gatling.core.Predef.{holdFor, _}
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class BaseSimulation extends Simulation {
  val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1ZTA3ODI1NDEyMzhiNzFmNGZlMTUxYTgiLCJhdXRoVG9rZW5zIjp7ImFjY2Vzc1Rva2VuIjoiNjM2OWIwZTdhOThjMWRmYTAxN2JlMzRjOWVkZWNmM2ViYzUzMjAxZSIsInJlZnJlc2hUb2tlbiI6IjgzZjFhN2Y3NWMzN2U4NmI2NmRjOTlhMTQzZjY4MWMyY2M4ODhjNDAifSwiaWF0IjoxNTk5NzU1NDA1fQ.8wD6FEKgmJDHVUFD2C8BdhdSSSPtnNjmJQbUlVmVlfM"

  /*
    Настройки для HTTP
    */
  val httpConf = http
    .baseUrl("https://task-dpss.kitsoft.kiev.ua")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val userAuth = scenario("User auth")
    .exec(
      http("get id.../auth")
        .get("https://id-dpss.kitsoft.kiev.ua/auth")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetServerList = scenario("User get server list ")
    .exec(
      http("get id.../authorise/eds/serverList")
        .get("https://id-dpss.kitsoft.kiev.ua/authorise/eds/serverList")
        .check(status.is(200))
    )

  val userCheckAuthInfo = scenario("User get auth/me ")
    .exec(
      http("get /auth/me")
        .get("/auth/me")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetUnits = scenario("User get units ")
    .exec(
      http("get /units")
        .get("/units")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetInboxesUnread = scenario("User get /user-inboxes/unread/count")
    .exec(
      http("get /user-inboxes/unread/count")
        .get("/user-inboxes/unread/count")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetUnreadMess = scenario("User get unread messages")
    .exec(
      http("get /messages/count-unread")
        .get("/messages/count-unread")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetMessages = scenario("User get messages")
    .exec(
      http("get /messages")
        .get("/messages")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetWorkflows10 = scenario("User get workflows with pagination 10")
    .exec(
      http("get /workflows with pagination 10")
        .get("/workflows?filters%5Btasks.deleted%5D=0&filters%5Bis_draft%5D=false&filters%5Bfiltered%5D=false&sort%5Btasks%5D%5Bfinished_at%5D=desc&count=10")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetWorkflows50 = scenario("User get workflows with pagination 50")
    .exec(
      http("get /workflows with pagination 50")
        .get("/workflows?filters%5Btasks.deleted%5D=0&filters%5Bis_draft%5D=false&filters%5Bfiltered%5D=false&sort%5Btasks%5D%5Bfinished_at%5D=desc&count=50")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetDrafts10 = scenario("User get drafts with pagination 10")
    .exec(
      http("get drafts with pagination 10")
        .get("/workflows?filters%5Btasks%5D%5Bdeleted%5D=0&filters%5Bis_draft%5D=true&filters%5Bfiltered%5D=false&sort%5Bdocuments%5D%5Bupdated_at%5D=desc&count=10")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetDrafts50 = scenario("User get drafts with pagination 50")
    .exec(
      http("get drafts with pagination 50")
        .get("/workflows?filters%5Btasks%5D%5Bdeleted%5D=0&filters%5Bis_draft%5D=true&filters%5Bfiltered%5D=false&sort%5Bdocuments%5D%5Bupdated_at%5D=desc&count=50")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetUnitTasks10 = scenario("User get unit tasks with pagination 10")
    .exec(
      http("get unit tasks with pagination 10")
        .get("/tasks?filters%5Bfinished%5D=0&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=unit&filters%5Bfiltered%5D=false&count=10")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetUnitTasks50 = scenario("User get unit tasks with pagination 50")
    .exec(
      http("get unit tasks with pagination 50")
        .get("/tasks?filters%5Bfinished%5D=0&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=unit&filters%5Bfiltered%5D=false&count=50")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetArchivedUnitTasks10 = scenario("User get archived unit tasks with pagination 10")
    .exec(
      http("get archived unit tasks with pagination 10")
        .get("/tasks?filters%5Bfinished%5D=1&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=unit&filters%5Bfiltered%5D=false&count=10")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetArchivedUnitTasks50 = scenario("User get archived unit tasks with pagination 50")
    .exec(
      http("get archived unit tasks with pagination 50")
        .get("/tasks?filters%5Bfinished%5D=1&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=unit&filters%5Bfiltered%5D=false&count=50")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetTasks10 = scenario("User get tasks with pagination 10")
    .exec(
      http("get /tasks with pagination 10")
        .get("/tasks?filters%5Bfinished%5D=0&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=me&filters%5Bfiltered%5D=false&count=10")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetTasks50 = scenario("User get tasks with pagination 50")
    .exec(
      http("get /tasks with pagination 50")
        .get("/tasks?filters%5Bfinished%5D=0&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=me&filters%5Bfiltered%5D=false&count=50")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetArchivedTasks10 = scenario("User get archived tasks with pagination 10")
    .exec(
      http("get archived tasks with pagination 10")
        .get("/tasks?filters%5Bfinished%5D=1&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=me&filters%5Bfiltered%5D=false&count=10")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetArchivedTasks50 = scenario("User get archived tasks with pagination 50")
    .exec(
      http("get archived tasks with pagination 50")
        .get("/tasks?filters%5Bfinished%5D=1&filters%5Bdeleted%5D=0&filters%5Bassigned_to%5D=me&filters%5Bfiltered%5D=false&count=50")
        .header("token", token)
        .check(status.is(200))
    )

  val userGetRegisters = scenario("User get registers key 15")
    .exec(
      http("get /registers/keys/15/records")
        .get("/registers/keys/15/records?offset=0&limit=615")
        .header("token", token)
        .check(status.is(200))
    )

  val userStartWorkflow = scenario("User start random workflow")
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
      )

  setUp(
    userAuth.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
        holdFor(1 minute),
        /*jumpToRps(2),
        holdFor(1 minute)*/
    ),
    userGetServerList.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userCheckAuthInfo.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetUnits.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetInboxesUnread.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetUnreadMess.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetMessages.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetWorkflows10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(2 minute)*/
    ),
    userGetWorkflows50.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetDrafts10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetDrafts50.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetUnitTasks10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetUnitTasks50.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetArchivedUnitTasks10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetArchivedUnitTasks50.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetTasks10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetTasks50.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetArchivedTasks10.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetArchivedTasks50.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userGetRegisters.inject(atOnceUsers(150)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    ),
    userStartWorkflow.inject(atOnceUsers(60)).throttle(
      reachRps(1) in (30 seconds),
      holdFor(1 minute),
      /*jumpToRps(2),
      holdFor(1 minute)*/
    )
  ).protocols(httpConf).maxDuration(10 minutes)
    .assertions(global.successfulRequests.percent.gt(95))
    .assertions(details("Auth").responseTime.max.lt(1000))
}
