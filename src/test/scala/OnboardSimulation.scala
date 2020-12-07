import io.gatling.core.Predef.{holdFor, _}
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class OnboardSimulation extends BaseSimulation {
  def baseRequest: BaseRequest = new BaseRequest()
  def checkEmail: CheckEmailRequest = new CheckEmailRequest()
  def checkPhone: CheckPhoneRequest = new CheckPhoneRequest()
  def postOnboard: PostOnboardRequest = new PostOnboardRequest()
  def putMetaOnboard: PutMetaOnboardRequest = new PutMetaOnboardRequest()
  def getOnboardTask: GetOnboardRequest = new GetOnboardRequest()
  def clearEmailConfirmation: ClearEmailConfirmationRequest = new ClearEmailConfirmationRequest()
  def clearPhoneConfirmation: ClearPhoneConfirmationRequest = new ClearPhoneConfirmationRequest()
  def postEBabyAdmin: PostEBabyAdminRequest = new PostEBabyAdminRequest()
  def setActivePassportTab: PutActivePassportTabRequest = new PutActivePassportTabRequest()
  def setSerieAndNumber: PutSerieAndNumberRequest = new PutSerieAndNumberRequest()
  def setIssuerDate: PutIssuerDateRequest = new PutIssuerDateRequest()
  def setIssuer: PutIssuerRequest = new PutIssuerRequest()
  def filterRegion: PostRegionOnboardRequest = new PostRegionOnboardRequest()
  def initStep: InitStepOnboardRequest = new InitStepOnboardRequest()
  def filterDistrict: PostDistrictOnboardRequest = new PostDistrictOnboardRequest()
  def filterCity: PostCityOnboardRequest = new PostCityOnboardRequest()
  def setCityFalse: PutCityFalseOnboardRequest = new PutCityFalseOnboardRequest()
  def setRegion: PutRegionOnboardRequest = new PutRegionOnboardRequest()
  def setDistrict: PutDistrictOnboardRequest = new PutDistrictOnboardRequest()
  def setStreetType: PutStreetTypeOnboardRequest = new PutStreetTypeOnboardRequest()
  def setStreetName: PutStreetNameOnboardRequest = new PutStreetNameOnboardRequest()
  def setAddress: PutAddressOnboardRequest = new PutAddressOnboardRequest()


  val commit: ScenarioBuilder = baseRequest.postRequest("Commit",
    "/tasks/${taskId}/commit", token, "{}")


  /**
   * Real user scenario when he commit onboard
   */

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
  PUT /documents/{id} - set region
  POST /registers/keys/410/records/filter?strict=true&limit=1000&control=documents.{id}.address.district - filter district
  POST /registers/keys/411/records/filter?strict=true&limit=1000&control=documents.{id}.address.city - filter city
  PUT /documents/{id} - set district
  POST /registers/keys/411/records/filter?strict=true&limit=1000&control=documents.{id}.address.city - filter city
  GET /tasks/{id}
  PUT /documents/{id} - set street type
  PUT /documents/{id} - set street name
  PUT /documents/{id} - set address
  POST commit
   */

  /**
   * I use this scenario for launch chain of requests
   */
  val script: ScenarioBuilder = scenario("Onboard")
    .exec(postOnboard.postOnboard)
    .exec(getOnboardTask.getOnboardTask)
    .exec(putMetaOnboard.putMetaOnboard)
    .exec(getOnboardTask.getOnboardTask)
    .exec(checkEmail.checkEmail)
    .exec(clearEmailConfirmation.clearEmailConfirmation)
    .exec(checkPhone.checkPhone)
    .exec(clearPhoneConfirmation.clearPhoneConfirmation)
    .exec(postEBabyAdmin.postEBabyAdmin)
    .exec(setActivePassportTab.setActivePassportTab)
    .exec(setSerieAndNumber.setSerieAndNumber)
    .exec(setIssuerDate.setIssuerDate)
    .exec(setIssuer.setIssuer)
    .exec(filterRegion.filterRegion)
    .exec(initStep.initStep)
    .exec(filterDistrict.filterDistrict)
    .exec(filterCity.filterCity)
    .exec(setCityFalse.setCityFalse)
    .exec(setCityFalse.setCityFalse)
    .exec(setRegion.setRegion)
    .exec(filterDistrict.filterDistrict)
    .exec(filterCity.filterCity)
    .exec(setDistrict.setDistrict)
    .exec(filterCity.filterCity)
    .exec(getOnboardTask.getOnboardTask)
    .exec(setStreetType.setStreetType)
    .exec(setStreetName.setStreetName)
    .exec(setAddress.setAddress)
    //.exec(commit) Not work when onboard post after registration

  /**
   * setUp block - configuration for scenario: rps, requests, maxDuration, maxRequests etc
   *
   * script.inject(atOnceUsers(sessions.toInt)) - use my script, inject all sessions(users) when start script, get count sessions from variable, must be Integer
   *
   * reachRps(rps.toInt) in (1 seconds) - up to rps (get from variable, must be Integer) after 1 sec
   *
   * holdFor(1 hour) - hold this rps 1 hour
   *
   * block throttle can be repeat
   *
   * protocols - use my default http configuration
   *
   * maxDuration - after 1 hour if all sessions not end script be stoped
   *
   * assertions - optional, can assert percent of successful responses, max response time etc
   */
  setUp(
    script.inject(rampUsers(sessions.toInt) during(10 second)).throttle(
      reachRps(rps.toInt) in (1 seconds),
      holdFor(1 hour)
    ),
  ).protocols(httpConf).maxDuration(1 hour)
    .assertions(global.successfulRequests.percent.gt(95))
}
