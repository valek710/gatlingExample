import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class PutDistrictOnboardRequest extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()

  val setDistrict: ScenarioBuilder = baseRequest.putRequest("Put district data", "/documents/${docId}", token,
    """
      |{
      |"properties":[
      | {
      |   "path":"address.district",
      |   "value":{
      |     "id":"896b5a40-0fb6-11ea-b75b-9375609372d6",
      |     "registerId":164,
      |     "keyId":410,
      |     "stringified":" Голосіївський",
      |     "isRelationId":"716",
      |     "atuId":"716",
      |     "atuNameId":"82329",
      |     "isRelationLink":"26",
      |     "atuParentId":"26",
      |     "level":"2",
      |     "type":"2",
      |     "status":"1",
      |     "code":"8036100000",
      |     "cvk":"",
      |     "name":"Голосіївський"
      |     }
      |   }
      | ]
      |}
      |""".stripMargin
  )

  val setDistrict1: ScenarioBuilder = baseRequest.putRequest("Put district data", "/documents/" + docId, token,
    """
      |{
      |"properties":[
      | {
      |   "path":"address.district",
      |   "value":{
      |     "id":"896b5a40-0fb6-11ea-b75b-9375609372d6",
      |     "registerId":164,
      |     "keyId":410,
      |     "stringified":" Голосіївський",
      |     "isRelationId":"716",
      |     "atuId":"716",
      |     "atuNameId":"82329",
      |     "isRelationLink":"26",
      |     "atuParentId":"26",
      |     "level":"2",
      |     "type":"2",
      |     "status":"1",
      |     "code":"8036100000",
      |     "cvk":"",
      |     "name":"Голосіївський"
      |     }
      |   }
      | ]
      |}
      |""".stripMargin
  )

  setUp(
    setDistrict1.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
