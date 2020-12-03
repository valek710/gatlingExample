import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class CabinetSimulation extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def authMeRequest: AuthMeRequest = new AuthMeRequest()
  def getDocumentTemplateRequest: GetDocumentTemplateRequest = new GetDocumentTemplateRequest()
  def getDraftsRequest: GetDraftsRequest = new GetDraftsRequest()
  def getDocumentTemplateByIdRequest: GetDocumentTemplateByIdRequest = new GetDocumentTemplateByIdRequest()
  def getDraftRequest: GetDraftRequest = new GetDraftRequest()
  def getInboxesRequest: GetInboxesRequest = new GetInboxesRequest()
  def getMessagesRequest: GetMessagesRequest = new GetMessagesRequest()
  def getTemplateRequest: GetTemplateRequest = new GetTemplateRequest()
  def getUnitsRequest: GetUnitsRequest = new GetUnitsRequest()
  def getWorkflowsRequest: GetWorkflowsRequest = new GetWorkflowsRequest()
  def getWorkflowDataRequest: GetWorkflowDataRequest = new GetWorkflowDataRequest()
  def getWorkflowTemplatesRequest: GetWorkflowTemplatesRequest = new GetWorkflowTemplatesRequest()
  def messUnreadCountRequest: MessUnreadCountRequest = new MessUnreadCountRequest()
  def myClosedTasksWithFilterRequest: MyClosedTasksWithFilterRequest = new MyClosedTasksWithFilterRequest()
  def myTasksRequest: MyTasksRequest = new MyTasksRequest()
  def myTasksWithFilterRequest: MyTasksWithFilterRequest = new MyTasksWithFilterRequest()
  def unitClosedTasksWithFilterRequest: UnitClosedTasksWithFilterRequest = new UnitClosedTasksWithFilterRequest()
  def unitTasksWithFilterRequest: UnitTasksWithFilterRequest = new UnitTasksWithFilterRequest()


  private val script: ScenarioBuilder = scenario("Scenario")
    .exec(authMeRequest.authMe)
    .exec(getDocumentTemplateRequest.getDocumentTemplates)
    .exec(getDraftsRequest.getDrafts)
    .exec(getDocumentTemplateByIdRequest.getDraft)
    .exec(getDraftRequest.getDraft)
    .exec(getTemplateRequest.getTemplate)
    .exec(getInboxesRequest.getInboxes)
    .exec(getMessagesRequest.getMessages)
    .exec(getUnitsRequest.getUnits)
    .exec(getWorkflowsRequest.getWorkflows)
    .exec(getWorkflowDataRequest.getWorkflow)
    .exec(getWorkflowTemplatesRequest.getTemplates)
    .exec(messUnreadCountRequest.getUnreadCount)
    .exec(myClosedTasksWithFilterRequest.getMyTasks)
    .exec(myTasksRequest.getMyTasks)
    .exec(myTasksWithFilterRequest.getMyTasks)
    .exec(unitClosedTasksWithFilterRequest.getUnitTasks)
    .exec(unitTasksWithFilterRequest.getUnitTasks)

  setUp(
    script.inject(rampUsers(sessions.toInt) during(20 second)).throttle(
      reachRps(rps.toInt) in (20 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
