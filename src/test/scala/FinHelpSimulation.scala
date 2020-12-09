import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class FinHelpSimulation extends BaseSimulation {
  def postFinHelpRequest: PostFinHelpRequest = new PostFinHelpRequest()
  def putMetaRequest: PutMetaRequest =  new PutMetaRequest()
  def getTaskRequest: GetTaskRequest = new GetTaskRequest()
  def myTasksUnreadCountRequest: MyTasksUnreadCountRequest = new MyTasksUnreadCountRequest()
  def myUnitUnreadCountRequest: MyUnitUnreadCountRequest = new MyUnitUnreadCountRequest()
  def postReceiversRegisterRequest: PostReceiversRegisterRequest = new PostReceiversRegisterRequest()
  def postFinPaymentRequest: PostFinPaymentRequest = new PostFinPaymentRequest()
  def putSecondStepDefaultValuesFinHelpRequest: PutSecondStepDefaultValuesFinHelpRequest = new PutSecondStepDefaultValuesFinHelpRequest()
  def putEmptyWorkplaceFinHelpRequest: PutEmptyWorkplaceFinHelpRequest = new PutEmptyWorkplaceFinHelpRequest()
  def putWorkNowFinHelpRequest: PutWorkNowFinHelpRequest = new PutWorkNowFinHelpRequest()
  def putWorkplaceAcceptFinHelpRequest: PutWorkplaceAcceptFinHelpRequest = new PutWorkplaceAcceptFinHelpRequest()
  def postFinPaymentFourthStepRequest: PostFinPaymentFourthStepRequest = new PostFinPaymentFourthStepRequest()
  def putIBANDefaultFinHelpRequest: PutIBANDefaultFinHelpRequest = new PutIBANDefaultFinHelpRequest()
  def putIBANFinHelpRequest: PutIBANFinHelpRequest = new PutIBANFinHelpRequest()
  def postValidateRequest: PostValidateRequest = new PostValidateRequest()
  def postPDFRequest: PostPDFRequest = new PostPDFRequest()
  def getPDFRequest: GetPDFRequest = new GetPDFRequest()
  def getDocumentRequest: GetDocumentRequest = new GetDocumentRequest()
  def postCommitRequest: PostCommitRequest = new PostCommitRequest()


  val script: ScenarioBuilder = scenario("FinHelpSimulation")
    .exec(postFinHelpRequest.postTask)
    .exec(putMetaRequest.putMeta)
    .exec(getTaskRequest.getTask)
    .exec(myTasksUnreadCountRequest.getUnreadCount)
    .exec(myUnitUnreadCountRequest.getUnreadCount)
    .exec(postReceiversRegisterRequest.postRecivers)
    .exec(getTaskRequest.getTask)
    .exec(postFinPaymentRequest.postPayment)
    .exec(postReceiversRegisterRequest.postRecivers)
    .exec(postFinPaymentRequest.postPayment)
    .exec(postReceiversRegisterRequest.postRecivers)
    .exec(postFinPaymentRequest.postPayment)
    .exec(getTaskRequest.getTask)
    .exec(putEmptyWorkplaceFinHelpRequest.putWorkplace)
    .exec(putWorkNowFinHelpRequest.putWorkNow)
    .exec(putWorkplaceAcceptFinHelpRequest.putAccept)
    .exec(postFinPaymentFourthStepRequest.postPayment)
    .exec(postFinPaymentFourthStepRequest.postPayment)
    .exec(getTaskRequest.getTask)
    .exec(putIBANDefaultFinHelpRequest.putIBAN)
    .exec(postFinPaymentFourthStepRequest.postPayment)
    .exec(getTaskRequest.getTask)
    .exec(putIBANFinHelpRequest.putIBAN)
    .exec(postFinPaymentFourthStepRequest.postPayment)
    .exec(getTaskRequest.getTask)
    .exec(postValidateRequest.postValidate)
    .exec(postPDFRequest.postPDF)
    .exec(getTaskRequest.getTask)
    .exec(getPDFRequest.getPDF)
    .exec(getDocumentRequest.getDocument)
    //.exec(postCommitRequest.postCommit)


  /*
    post task
    put meta
    get task
    get unread task - maybe we can delete this route
    get unread unit task - maybe we can delete this route
    post receivers - 69 key
    get task
    post payment - 71 key
    post receivers - 69 key =)
    post payment - 71 key =)
    post receivers - 69 key =)
    post payment - 71 key =)
    get task
    put default values for second task
    put empty workplace
    put workNow = 1
    put accept = 0
    post payment 4 step - 71 key
    post payment 4 step - 71 key
    get task
    put iban = UA
    post payment 4 step - 71 key
    get task
    put iban
    post payment 4 step - 71 key
    get task
    post validate
    post PDF
    get task
    get PDF
    get document
    post commit
   */


  setUp(
    script.inject(atOnceUsers(sessions.toInt)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    )
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
