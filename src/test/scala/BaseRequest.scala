import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

class BaseRequest {
  def postRequest(scenarioName: String, url: String, token: String, body: String): ScenarioBuilder = {
    scenario(scenarioName)
      .exec(
        http(scenarioName)
          .post(url)
          .headers(
            Map(
              "token" -> token,
              "Content-Type" -> "application/json"
            )
          )
          .body(StringBody(body.stripMargin))
          .check(status.is(200))
      )
  }

  def putRequest(scenarioName: String, url: String, token: String, body: String): ScenarioBuilder = {
    scenario(scenarioName)
      .exec(
        http(scenarioName)
          .put(url)
          .headers(
            Map(
              "token" -> token,
              "Content-Type" -> "application/json"
            )
          )
          .body(StringBody(body.stripMargin))
          .check(status.is(200))
      )
  }

  def getRequest(scenarioName: String, url: String, token: String): ScenarioBuilder = {
    scenario(scenarioName)
      .exec(
        http(scenarioName)
          .get(url)
          .headers(
            Map(
              "token" -> token,
              "Content-Type" -> "application/json"
            )
          )
          .check(status.is(200))
      )
  }
}
