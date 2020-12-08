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
  def putWorkNowFinHelpRequest: PutWorkNowFinHelpRequest = new putWorkNowFinHelpRequest()
  def putWorkplaceAcceptFinHelpRequest: PutWorkplaceAcceptFinHelpRequest = new PutWorkplaceAcceptFinHelpRequest()
  def postFinPaymentFourthStepRequest: PostFinPaymentFourthStepRequest = new PostFinPaymentFourthStepRequest()







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
   */


  setUp(
    getDashboard1.inject(atOnceUsers(ses1)).throttle(
      reachRps(rps1) in (1 seconds),
      holdFor(1 hour)
    ),
    postFinHelp1.inject(atOnceUsers(ses2)).throttle(
      reachRps(rps2) in (1 seconds),
      holdFor(1 hour)
    ),
    getStatus1.inject(atOnceUsers(ses1)).throttle(
      reachRps(rps1) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
