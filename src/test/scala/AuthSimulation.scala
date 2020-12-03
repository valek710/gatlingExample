import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

class AuthSimulation extends BaseSimulation {
  private val userAuth: ScenarioBuilder = scenario("User auth")
    .exec(
      http("get id.../auth")
        .get(idUrl + "auth")
        .header("token", token)
        .check(status.is(200))
    )

  private val userGetServerList: ScenarioBuilder = scenario("User get server list ")
    .exec(
      http("get id.../authorise/eds/serverList")
        .get(idUrl + "authorise/eds/serverList")
        .check(status.is(200))
    )

  /* GET /authorise/eds/sign
    POST /authorise/eds
    GET /auth
    POST /sign_up/confirmation
    GET /test/ping
    POST /auth/login
    GET /auth/me
    GET /units
    GET /tasks/{id} - get onboard task
    GET /user-inboxes/unread/count
    GET /messages/count-unread
    GET /tasks/unread/count?filters[finished]=0&filters[deleted]=0&filters[assigned_to]=me&filters[filtered]=false
    GET /tasks/unread/count?filters[finished]=0&filters[deleted]=0&filters[assigned_to]=unit&filters[filtered]=false
    GET /custom/diia/october/header
    GET /custom/diia/october/categories
    GET /custom/diia/october/footer
    GET /modules
    GET /document-templates/3004001
    PUT /tasks/{id}/meta - put meta onboard task
    GET /tasks/unread/count?filters[finished]=0&filters[deleted]=0&filters[assigned_to]=me&filters[filtered]=false
    GET /tasks/unread/count?filters[finished]=0&filters[deleted]=0&filters[assigned_to]=unit&filters[filtered]=false
  */
}
