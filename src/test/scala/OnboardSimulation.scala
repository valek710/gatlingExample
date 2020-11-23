import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

class OnboardSimulation extends BaseSimulation {
  private val postOnboard: ScenarioBuilder = scenario("Post onboard")
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
          jsonPath("$.data[*].id").saveAs("taskId")
        )
        .check(
          jsonPath("$.data[*].documentId").saveAs("docId")
        )
    )

  private val putMetaOnboard: ScenarioBuilder = scenario("Put meta onboard")
    .exec(
      http("put meta Onboard")
        .put("/tasks/${taskId}/meta")
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
              | {
              | "meta":{
              | "isRead":true
              | }
              |}""".stripMargin
          )
        )
        .check(status.is(200))
        .check(
          jsonPath("$.data[*].id").saveAs("taskId")
        )
        .check(
          jsonPath("$.data[*].documentId").saveAs("docId")
        )
    )

  private val getOnboardTask: ScenarioBuilder = scenario("Get onboard task")
    .exec(
      http("Get onboard task")
        .get("/tasks/${taskId}")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .check(status.is(200))
    )

  private val checkEmail: ScenarioBuilder = scenario("Check email")
    .exec(
      http("Check email")
        .post("/users/email/check")
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
              |"email":"v.kostromin@kitsoft.kiev.ua"
              |}
              |""".stripMargin
          )
        )
        .check(status.is(200))
    )

  private val clearEmailConfirmation: ScenarioBuilder = scenario("Clear email confirmation")
    .exec(
      http("Clear email confirmation")
        .put("/documents/${docId}")
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
              |"properties":[
              |{
              |"path":"emailConfirmation.confirmation",
              |"previousValue":null
              |}
              |]
              |}
              |""".stripMargin
          )
        )
        .check(status.is(200))
    )

  private val clearPhoneConfirmation: ScenarioBuilder = scenario("Clear phone confirmation")
    .exec(
      http("Clear phone confirmation")
        .put("/documents/${docId}")
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
              |"properties":[
              |{
              |"path":"phoneConfirmation.confirmation",
              |"previousValue":null
              |}
              |]
              |}
              |""".stripMargin
          )
        )
        .check(status.is(200))
    )

  private val postEBabyAdmin: ScenarioBuilder = scenario("Post eBaby admin")
    .exec(
      http("Post eBaby admin")
        .post("/registers/keys/427/records/filter?strict=true&limit=1000&control=documents.${docId}.applicant.eBabyAdminRegistry")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .body(
          StringBody("{}")
        )
        .check(status.is(200))
    )

  private val setActivePassportTab: ScenarioBuilder = scenario("Put active passport tab")
    .exec(
      http("Put active passport tab")
        .put("/documents/${id}")
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
              |""".stripMargin)
        )
        .check(status.is(200))
    )

  private val setSerieAndNumber: ScenarioBuilder = scenario("Put serie and number")
    .exec(
      http("Put serie and number")
        .put("/documents/${id}")
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
              | "properties":[
              |   {"path":"pasport.tabs.passport.pasNumber.serie",
              |   "value":"ЕН"
              |   },
              |   {
              |   "path":"pasport.tabs.passport.pasNumber.number",
              |   "value":"123456"
              |   }
              | ]
              |}
              |""".stripMargin)
        )
        .check(status.is(200))
    )

  private val setIssuerDate: ScenarioBuilder = scenario("Put issuerDate")
    .exec(
      http("Put issuerDate")
        .put("/documents/${id}")
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
              | "properties":[
              |     {
              |     "path":"pasport.tabs.passport.date.day",
              |     "value":"19"
              |     },
              |     {
              |     "path":"pasport.tabs.passport.date.month",
              |     "value":"01"
              |     },
              |     {
              |     "path":"pasport.tabs.passport.date.year",
              |     "value":"2015"
              |     }
              |   ]
              |}
              |""".stripMargin)
        )
        .check(status.is(200))
    )

  private val setIssuer: ScenarioBuilder = scenario("Put issuer")
    .exec(
      http("Put issuer")
        .put("/documents/${id}")
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
              | "properties":[
              |   {
              |   "path":"pasport.tabs.passport.pasIssurer",
              |   "value":"Рокитнянським РВ ГУ МВС України в Київській області"
              |   }
              | ]
              |}
              |""".stripMargin)
        )
        .check(status.is(200))
    )

  private val filterRegion: ScenarioBuilder = scenario("Post region")
    .exec(
      http("Post region")
        .post("/registers/keys/408/records/filter?strict=true&limit=1000&control=documents.${docId}.address.region")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .body(StringBody("{}"))
        .check(status.is(200))
    )

  private val initStep: ScenarioBuilder = scenario("Init step")
    .exec(
      http("Init step")
        .put("documents/${docId}")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .body(StringBody(
          """
            |{
            | "properties":[
            |   {
            |   "path":"address",
            |   "value":
            |     {
            |     "street":{},
            |     "building":{},
            |     "apt":{}
            |     }
            |   }
            | ]
            |}
            |""".stripMargin))
        .check(status.is(200))
    )

  private val filterEmptyDistrict: ScenarioBuilder = scenario("Post empty district")
    .exec(
      http("Post empty district")
        .post("/registers/keys/410/records/filter?strict=true&limit=1000&control=documents.${docId}.address.district")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .body(StringBody("{}"))
        .check(status.is(200))
    )

  private val filterEmptyCity: ScenarioBuilder = scenario("Post empty city")
    .exec(
      http("Post empty city")
        .post("/registers/keys/411/records/filter?strict=true&limit=1000&control=documents.${docId}.address.city")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .body(StringBody("{}"))
        .check(status.is(200))
    )

  private val setCityFalse: ScenarioBuilder = scenario("Put city false")
    .exec(
      http("Put city false")
        .put("documents/${docId}")
        .headers(
          Map(
            "token" -> token,
            "Content-Type" -> "application/json"
          )
        )
        .body(StringBody(
          """
            |{
            | "properties":[
            | {
            |   "path":"address.city",
            |   "value":false
            |   }
            | ]
            |}
            |""".stripMargin))
        .check(status.is(200))
    )

  /*
  SECOND STEP (/tasks/onBoarding/email)

  POST /tasks/     id = 3004001 - init onboard task
  PUT /tasks/{id}/meta - put meta to onboard task
  GET /tasks/{id} - get onboard task


  POST /registers/keys/443/records/filter?strict=true&limit=1000&control=documents.{id}.email.betaTester
  POST /registers/keys/54/records/filter?strict=true&limit=1000&control=documents.{id}.email.betaTester__building
  POST /registers/keys/443/records/filter?strict=true&limit=1000&control=documents.{id}.email.betaTester
  POST /registers/keys/54/records/filter?strict=true&limit=1000&control=documents.{id}.email.betaTester__building
  POST registers/keys/443/records/filter?strict=true&limit=1000&control=documents.{id}.email.betaTester
  POST /registers/keys/54/records/filter?strict=true&limit=1000&control=documents.{id}.email.betaTester__building
  GET /tasks/{id}
  PUT /documents/{id}
  POST /registers/keys/54/records/filter?strict=true&limit=1000&control=documents.{id}.email.betaTester__building
  GET /tasks/{id}


  THIRD STEP (/tasks/onBoarding/emailConfirmation)

  PUT /documents/{id} - set email
  GET /tasks/{id}
  POST /users/email/check - check email
  PUT /users/email/change - get code
  PUT /documents/{id} - email confirmation
  GET /tasks/{id}
  POST /users/email/confirm - confirm email
  PUT /documents/{id} - set confirmed=true in document


  FOURTH STEP (/tasks/onBoarding/phone)

  PUT /documents/{id} - clear object path emailConfirmation.confirmation
  PUT /documents/{id} - set phone


  FIFTH STEP (/tasks/onBoarding/phoneConfirmation)

  GET /users/phone/already_used?phone=380669588307 - check if phone used
  POST /users/phone/send_sms_for_phone_verification - verify phone
  PUT /documents/{id} - set sending info to document
  POST /users/phone/verify - set code
  PUT /documents/{id} - set confirmation status


  SIXTH STEP (/tasks/onBoarding/applicant)

  PUT /documents/{id} - clear object phoneConfirmation.confirmation
  POST /registers/keys/427/records/filter?strict=true&limit=1000&control=documents.{id}.applicant.eBabyAdminRegistry - check if user admin
  POST /registers/keys/427/records/filter?strict=true&limit=1000&control=documents.{id}.applicant.eBabyAdminRegistry - check if user admin
  GET /tasks/{id}


  SEVENTH STEP (/tasks/onBoarding/birthday) - no requests if birthDate actual


  EIGHTH STEP (/tasks/onBoarding/pasport)

  PUT /documents/{id} - set active passport tab (by default)
  PUT /documents/{id} - set serie and number
  PUT /documents/{id} - set issue date
  PUT /documents/{id} - set issuer


  LAST STEP (/tasks/onBoarding/address)

  POST /registers/keys/408/records/filter?strict=true&limit=1000&control=documents.{id}.address.region - get regions
  PUT /documents/{id} - init step
  POST /registers/keys/410/records/filter?strict=true&limit=1000&control=documents.{id}.address.district - filter empty district
  POST /registers/keys/411/records/filter?strict=true&limit=1000&control=documents.{id}.address.city - filter empty city
  PUT /documents/{id} - set city=false
  PUT /documents/{id} - set city=false
  POST /registers/keys/408/records/filter?strict=true&limit=1000&control=documents.{id}.address.region - get regions
  POST /registers/keys/410/records/filter?strict=true&limit=1000&control=documents.{id}.address.district - filter empty district
  POST /registers/keys/411/records/filter?strict=true&limit=1000&control=documents.{id}.address.city - filter empty city
   */
}
