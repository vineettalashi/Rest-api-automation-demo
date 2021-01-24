package com.qe.vt.steps;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qe.vt.apihelper.RequestSpecificationBuilder;
import com.qe.vt.apihelper.RestApiController;
import com.qe.vt.constants.Constants;
import com.qe.vt.dataprovider.ValidationData;
import com.qe.vt.pojo.RequestPojo;
import com.qe.vt.pojo.ResponsePojo;
import com.qe.vt.utils.JsonUtils;
import com.qe.vt.utils.PropertiesLoader;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValidationsSteps {
	Logger logger = LoggerFactory.getLogger(ValidationsSteps.class);
	RequestSpecificationBuilder reqstSpecBuilder;
	RequestPojo requestPojo;List<RequestPojo> batchRequest;
	ResponsePojo response;List<ResponsePojo> batchResponse;
	String jsonToPost;StringBuilder finalJsonToPostBuilder = new StringBuilder("[");
	String finalJsonToPost;
	String jsonFile;
	PropertiesLoader propLoader = PropertiesLoader.getInstance();
	Scenario scenario;
	
	//Hooks example
	@Before
	public void beforeScenario(Scenario scene) {
		logger.info("Scenario Execution Started: "+scene.getName());
		scenario=scene;
	}
	
	@After
	public void afterScenario() {
		logger.info("Scenario Execution Ended: "+scenario.getName());
	}
	
	
	//Reading Valid Data JSON file and Deserializing it into String and RequestPojo
	@Given("User has valid data to post for {string}")
	public void UserHasValidDataToPostPrdtType(String type) throws IOException, IllegalAccessException, InstantiationException { 		
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile(type), ValidationData.getValidDataForTradeSpotForward());
	    requestPojo=JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
	    scenario.log(jsonToPost);
	    scenario.log(requestPojo.toString());
	}
	
	//Reading Valid Data Batch JSON file and Deserializing it into String and RequestPojo
	@Given("User has valid data to post for validateBatch api")
	public void UserHasValidDataToPostBatchApiRequst() throws IllegalAccessException, InstantiationException, IOException {
		AtomicInteger counter = new AtomicInteger(0);
		JSONArray jsonArray = JsonUtils.getJSONArrayFromJsonFile(getJsonFile("Batch"));
		jsonArray.forEach(jsonObj->
	    		{
					jsonToPost = JsonUtils.getUpdatedJson((JSONObject)jsonObj, ValidationData.getValidDataForBatchApi().get(counter.getAndIncrement()));
					finalJsonToPostBuilder.append(jsonToPost+",");
				}
	    		);
		finalJsonToPostBuilder.replace(finalJsonToPostBuilder.length()-1, finalJsonToPostBuilder.length(),"]");
		finalJsonToPost=finalJsonToPostBuilder.toString();
		batchRequest = Arrays.asList(JsonUtils.getArrayOfRequestPojoClassFromJsonString(finalJsonToPost));
		scenario.log(finalJsonToPostBuilder.toString());
		scenario.log(batchRequest.toString());
	}
	
	//Reading Valid Data Batch JSON file , updates some field with invalid data and Deserialize it into String and RequestPojo
	@Given("User has invalid data to post for validateBatch api")
	public void UserHasInvalidDataToPostBatchApiRequst() throws IllegalAccessException, InstantiationException, IOException {
		JSONArray jsonArray = JsonUtils.getJSONArrayFromJsonFile(getJsonFile("Batch"));
		AtomicInteger counter = new AtomicInteger(0);
		jsonArray.forEach(jsonObj->
	    		{
					jsonToPost = JsonUtils.getUpdatedJson((JSONObject)jsonObj, ValidationData.getInvalidDataForBatchApi().get(counter.getAndIncrement()));
					finalJsonToPostBuilder.append(jsonToPost+",");
				}
	    		);
		finalJsonToPostBuilder.replace(finalJsonToPostBuilder.length()-1, finalJsonToPostBuilder.length(),"]");
		finalJsonToPost=finalJsonToPostBuilder.toString();
		batchRequest = Arrays.asList(JsonUtils.getArrayOfRequestPojoClassFromJsonString(finalJsonToPost));
		scenario.log(finalJsonToPostBuilder.toString());
		scenario.log(batchRequest.toString());
	}
	
	//Improvement as suggested by Tauseef
	//Reading Valid Data Batch JSON file , updates some field with invalid data and Deserialize it into String and RequestPojo
	@Given("Improvement: User has invalid data to post for validateBatch api")
	public void UserHasInvalidDataToPostBatchApiRequst1() throws JSONException, IOException, IllegalAccessException, InstantiationException {
		JSONArray jsonArray = JsonUtils.getJSONArrayFromJsonFile(getJsonFile("Batch"));
		JSONArray updatejsonArray = new JSONArray();
		JSONObject updatedJsonObj;
		for(int i=0;i<jsonArray.length();i++){
			updatedJsonObj = JsonUtils.getUpdatedJsonObject(jsonArray.getJSONObject(i), ValidationData.getInvalidDataForBatchApi(jsonArray.getJSONObject(i)));
			updatejsonArray.put(i, updatedJsonObj);
		}
		finalJsonToPost = updatejsonArray.toString();
		batchRequest = Arrays.asList(JsonUtils.getArrayOfRequestPojoClassFromJsonString(finalJsonToPost));
		scenario.log("Request JSON String: "+finalJsonToPost);
		scenario.log("Array of Request Pojo: "+batchRequest.toString());
	}
	
	@Given("^User has valid data to post for Options with European Style$")
	public void UserHasValidDataToPostOptionsEuropean() throws IOException { 		
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithoutExerciseDate"), ValidationData.getValidDataForOptionsWithEuropeanStyle());	
	    scenario.log(jsonToPost);
	}
	
	@Given("^User has valid data to post for Options with American Style$")
	public void UserHasValidDataToPostOptionsAmerican() throws IOException { 		
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithExerciseDate"), ValidationData.getValidDataForOptionsWithEuropeanStyle());	
	}
		
	//Demo: Posting the json object as String
	@When("^User posts the json request$")
	public void UserPostTheData() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    response = restApiController.executePostMethodWithRequestBodyAsString(propLoader.getValidateUrl(), jsonToPost);
	    scenario.log(response.toString());
	}
	
	//Demo: Posting the json object as RequestPojo object
	@When("^User posts the json request as object$")
	public void UserPostTheDataAsObject() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    response = restApiController.executePostMethodWithRequestBodyAsObject(propLoader.getValidateUrl(), requestPojo);
	    scenario.log(response.toString());
	}
	
	//Demo: Posting the json object as String for validateBatch Api
	@When("^User posts the json request for validate batch api as string$")
	public void UserPostTheDataForBatchAsString() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    batchResponse = restApiController.executePostMethodWithRequestBodyAsStringBatch(propLoader.getValidateBatchUrl(), finalJsonToPostBuilder.toString());
	    logger.info("Batch Response : {}",batchResponse);
	    scenario.log(batchResponse.toString());
	}
	
	//Demo: Posting the json object as RequestPojo object for validateBatch Api
	@When("^User posts the json request for validate batch api as object$")
	public void UserPostTheDataForBatchAsObject() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    batchResponse = restApiController.executePostMethodWithRequestBodyAsListOfObject(propLoader.getValidateBatchUrl(), batchRequest);
	    logger.info("Batch Response : "+ batchResponse);
	    scenario.log(batchResponse.toString());
	}
	
	//Verify the Success status in Response
	@Then("^Verify the response is valid$")
	public void verifyResponse(){
	    Assert.assertEquals("SUCCESS",response.getStatus());
	    Assert.assertNull(response.getMessages());
	}
	
	//Verify the Success status in Response for batch api
	@Then("^Verify the batch response is valid$")
	public void verifyBatchResponse(){
		batchResponse.stream().forEach(response->{
	    	Assert.assertEquals("SUCCESS",response.getStatus());
	    	Assert.assertNull(response.getMessages());
		});
	}
	
	//Verify the parameterized status in Response for Batch Api
	@Then("Verify the batch response status is {string}")
	public void verifyBatchResponseMix(String status){
		batchResponse.stream().forEach(response->{
	    	Assert.assertEquals(status,response.getStatus());
		});
	}
	
	@Then("Verify the response status is {string}")
	public void verifyResponseStatus(String expectedStatus) {
	    Assert.assertEquals(expectedStatus,response.getStatus());
	}
	
	//Common Method for validating response
	@Then("Verify the response contains the {string} error message")
	public void responseContainsTheRequiredErrorMessage(String messages) throws Throwable {
		for(String message : messages.split("\\|")) {
			validateMsgInResponse(message);
		 }
	   }
	
	//Data table examples
	@Then("Verify the response contains the error messages")
	public void responseContainsTheRequiredErrorMessage(List<String> messages) throws Throwable {
		for(String message : messages) {
			validateMsgInResponse(message);
		 }
	   }
	
	private void validateMsgInResponse(String message) {
		String msg = message.trim();
		if(null==response.getException()) {
			Assert.assertEquals("ERROR",response.getStatus());
			Assert.assertTrue("Expected Error: "+msg+"Actual Error: "+response.getMessages().get(0),response.getMessages().get(0).contains(msg));
		}
		else {
			if(response.getStatus().equals(Constants.BAD_REQUEST)) Assert.assertEquals("Bad Request",response.getError());
			else if(response.getStatus().equals(Constants.INTERNAL_SERVER_ERROR)) Assert.assertEquals("Internal Server Error",response.getError());
			Assert.assertTrue("Expected Error: "+msg+"Actual Error: "+response.getMessage(),response.getMessage().contains(msg));
		}
		
	}

	//Demo: When Values date is older than Trade date for non-options product
	@Given("User has Value Date older than Trade Date for product type {string}")
	public void UserHasInvalidValueDate(String type) throws IOException, IllegalAccessException, InstantiationException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile(type), ValidationData.getValueDateOlderThanTradeDateData());	
	    requestPojo = JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
	    scenario.log(requestPojo.toString());
	}
	
	//Verify the complete error message along with dates
	@Then("^Verify the response contains correct error message when value date is before trade date$")
	public void verifyResponseContainsCorrectErrorMessageValueDateBeforeTradeDate() {
		Assert.assertEquals("Value date "+requestPojo.getValueDate()+" cannot be null and it has to be after trade date "+requestPojo.getTradeDate(),response.getMessages().get(0).trim());
	}
	
	@Given("User has the Value Date on weekend for product type {string}")
	public void UserHasWeekendValueDate(String type) throws IOException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile(type), ValidationData.getWeekendValueDateData());	
	}
	
	@Given("User has Invalid counterparty for product type {string}")
	public void UserHasInvalidCustomerName(String type) throws IOException, IllegalAccessException, InstantiationException {
		jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile(type), ValidationData.getInvalidCounterpartyData());
		requestPojo = JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
		scenario.log(requestPojo.toString());
	}
	
	@Then("^Verify the response contains correct error message when Invalid Customer is provided$")
	public void verifyResponseContainsCorrectErrorMessageForInvalidCounterparty() {
		Assert.assertEquals("Counterparty ["+requestPojo.getCustomer()+"] is not supported. Supported counterparties: [[PLUTO2, PLUTO1]]",response.getMessages().get(0).trim());
	}
	
	@Given("User has Invalid Legal entity for product type {string}")
	public void UserHasInvalidLE(String type) throws IOException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile(type), ValidationData.getInvalidLegalEntityData());	
	}
	
	@Given("^User has Invalid Product Type in input$")
	public void UserHasInvalidPrdtType() throws IOException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("Spot"), ValidationData.getInvalidProductTypeData());	
	}
	
	@Given("^User has Invalid Style in input$")
	public void UserHasInvalidStyle() throws IOException, IllegalAccessException, InstantiationException {
		jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithoutExerciseDate"), ValidationData.getInvalidStyleData());
		requestPojo = JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
		scenario.log(requestPojo.toString());	
	}
	
	@Given("User has Invalid ISO Currency for product type {string}")
	public void UserHasInvalidISOCurrency(String type) throws IOException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile(type), ValidationData.getInvalidCurrencyPairData());	
	}
	
	@Given("^User has data having Expiry Date after Delivery Date$")
	public void UserHasInValidDataExpiryDateAfterDeliveryDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithExerciseDate"), ValidationData.getExpiryDateAfterDeliveryDateInvalidData());
	    scenario.log(jsonToPost);
	}
	
	@Given("^User has data having Premium Date after Delivery Date$")
	public void UserHasInValidDataPremiumDateAfterDeliveryDate() throws IOException, IllegalAccessException, InstantiationException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithExerciseDate"), ValidationData.getPremiumDateAfterDeliveryDateInvalidData());
		requestPojo = JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
		scenario.log(requestPojo.toString());
	}
	
	@Then("^Verify the response contains the correct error message when Premium Date is after Delivery Date$")
	public void verifyErrorMsgWhenInValidDataPremiumDateAfterDeliveryDate() throws IOException {	
		Assert.assertEquals("Premium date "+requestPojo.getPremiumDate()+" has to be before delivery date "+requestPojo.getDeliveryDate(),response.getMessages().get(0).trim());
	}
	
	@Then("^Verify the response contains the correct error message when Invalid Style is provided$")
	public void verifyErrorMsgWhenInValidStyleIsProvided() throws IOException {	
		Assert.assertEquals("Invalid option style [ "+requestPojo.getStyle()+" ]. Valid option styles are: [AMERICAN, EUROPEAN]",response.getMessages().get(0).trim());
	}
	
	@Given("^User has data having Exercise Start Date before Trade Date$")
	public void UserHasInValidDataExerciseStartDateBeforeTradeDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithExerciseDate"), ValidationData.getExerciseStartDateBeforeTradeDateInvalidData());	
	}
	
	@Given("^User has data having Exercise Start Date after Expiry Date$")
	public void UserHasInValidDataExerciseStartDateAfterExpiryDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithExerciseDate"), ValidationData.getExerciseStartDateAfterExpiryDateInvalidData());	
	}
	
	@Given("^User has data with American Style and without Excercise Start Date$")
	public void UserHasAmericanStyleWithoutExerciseStartDate() throws IOException, IllegalAccessException, InstantiationException {	
	    jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithoutExerciseDate"), ValidationData.getAmericanStyleWithoutStartExerciseDateData());
	    requestPojo = JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
	    scenario.log(requestPojo.toString());
	}
	
	@Given("^User has data with European Style and with Excercise Start Date$")
	public void UserHasEuropeanStyleWithExerciseStartDate() throws IOException, IllegalAccessException, InstantiationException {
		jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithExerciseDate"), ValidationData.getEuropeanStyleWithStartExerciseDateData());
		requestPojo = JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
		scenario.log(requestPojo.toString());	
	}
	
	@Given("^User has amount1 and amount2 as zero , Premium is zero and PayCcy is empty in input data$")
	public void UserHasInvalidAmountPayCcyPremium() throws IOException, IllegalAccessException, InstantiationException {	
		jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithExerciseDate"), ValidationData.getInvalidAmountsInvalidPayCcyInvalidPremium());
		requestPojo = JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
		scenario.log(requestPojo.toString());	
	}
	
	@Given("User has amount1 and amount2 as zero , Rate is zero and Trader is empty in input data for product type {string}")
	public void UserHasInvalidAmountRateTrader(String type) throws IOException, IllegalAccessException, InstantiationException {	
		jsonToPost = JsonUtils.getJsonStringToPost(getJsonFile("OptionsWithExerciseDate"), ValidationData.getInvalidAmountsInvalidRateInvalidTrader());
		requestPojo = JsonUtils.getPojoClassFromJsonString(jsonToPost, RequestPojo.class);
		scenario.log(requestPojo.toString());	
	}
	
	//Improvement as suggested by Tauseef
	//Get Json File from using classloader
	public File getJsonFile(String type) {
		File file = null;
		try {
			if(type.equalsIgnoreCase("Batch"))
					file=new File(this.getClass().getClassLoader().getResource(Constants.JSON_PATH_BATCH+File.separator+propLoader.getValue("Batch")).toURI());
			else
				file=new File(this.getClass().getClassLoader().getResource(Constants.JSON_PATH+File.separator+propLoader.getValue(type)).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	return file;
	}
}