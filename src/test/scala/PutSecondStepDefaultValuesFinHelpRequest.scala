import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutSecondStepDefaultValuesFinHelpRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val putData: ScenarioBuilder = baseRequest.putRequest("Put default values second step",
    "/documents/${docId}", token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"applicantInfo.notInRegiser",
      |   "value":false,
      |   "previousValue":null
      |   },
      |   {
      |   "path":"applicantInfo.notApproved",
      |   "value":false,
      |   "previousValue":null
      |   },
      |   {
      |   "path":"applicantInfo.isPayed",
      |   "value":false,
      |   "previousValue":null
      |   },
      |   {
      |   "path":"applicantInfo.isPaymentInProgress",
      |   "value":false,
      |   "previousValue":null
      |   }
      | ]
      |}
      |""".stripMargin)

  val putData1: ScenarioBuilder = baseRequest.putRequest("Put default values second step",
    "/documents/" + docId, token,
    """
      |{
      |"properties":
      | [
      |   {
      |   "path":"applicantInfo.notInRegiser",
      |   "value":false,
      |   "previousValue":null
      |   },
      |   {
      |   "path":"applicantInfo.notApproved",
      |   "value":false,
      |   "previousValue":null
      |   },
      |   {
      |   "path":"applicantInfo.isPayed",
      |   "value":false,
      |   "previousValue":null
      |   },
      |   {
      |   "path":"applicantInfo.isPaymentInProgress",
      |   "value":false,
      |   "previousValue":null
      |   }
      | ]
      |}
      |""".stripMargin)

  setUp(
    putData1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
