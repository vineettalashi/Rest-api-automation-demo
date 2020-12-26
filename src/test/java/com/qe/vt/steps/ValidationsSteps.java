package com.qe.vt.steps;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qe.vt.helper.Constants;
import com.qe.vt.helper.PropertiesLoader;
import com.qe.vt.helper.RequestSpecificationBuilder;
import com.qe.vt.helper.RestApiController;
import com.qe.vt.helper.ValidationData;
import com.qe.vt.pojo.RequestPojo;
import com.qe.vt.pojo.ResponsePojo;
import com.qe.vt.utils.JsonUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValidationsSteps {
	Logger logger = LoggerFactory.getLogger(ValidationsSteps.class);
	RequestSpecificationBuilder reqstSpecBuilder;
	RequestPojo requestPojo;List<RequestPojo> batchRequest;
	ResponsePojo response;List<ResponsePojo> batchResponse;
	String jsonToPost;StringBuilder finalJsonToPost = new StringBuilder("[");
	String jsonFile;
	ObjectMapper mapper = new ObjectMapper();
	PropertiesLoader propLoader = PropertiesLoader.getInstance();
	
	@Given("User has valid data to post for {string}")
	public void UserHasValidDataToPostPrdtType(String type) throws IOException { 		
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getValidDataForTradeSpotForward());
	}
	
	@Given("User has valid data to post for validateBatch api")
	public void UserHasValidDataToPostBatchApiRequst() throws JsonMappingException, JsonProcessingException {
		AtomicInteger counter = new AtomicInteger(0);
		JSONArray jsonArray = JsonUtils.getJSONArrayFromJsonFile(new File(getJsonFilePath("Batch")));
		jsonArray.forEach(jsonObj->
	    		{
					try {
						jsonToPost = JsonUtils.getUpdatedJson((JSONObject)jsonObj, ValidationData.getValidDataForBatchApi().get(counter.getAndIncrement()));
						finalJsonToPost.append(jsonToPost+",");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
	    		);
		finalJsonToPost.replace(finalJsonToPost.length()-1, finalJsonToPost.length(),"]");
		batchRequest = Arrays.asList(mapper.readValue(finalJsonToPost.toString(), RequestPojo[].class));
		logger.info("Batch Request String: "+ finalJsonToPost);
		logger.info("Batch Request Object: "+ batchRequest);
	}
	
	@Given("User has invalid data to post for validateBatch api")
	public void UserHasInvalidDataToPostBatchApiRequst() throws JsonMappingException, JsonProcessingException {
		JSONArray jsonArray = JsonUtils.getJSONArrayFromJsonFile(new File(getJsonFilePath("Batch")));
		AtomicInteger counter = new AtomicInteger(0);
		jsonArray.forEach(jsonObj->
	    		{
					try {
						jsonToPost = JsonUtils.getUpdatedJson((JSONObject)jsonObj, ValidationData.getInvalidDataForBatchApi().get(counter.getAndIncrement()));
						finalJsonToPost.append(jsonToPost+",");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
	    		);
		finalJsonToPost.replace(finalJsonToPost.length()-1, finalJsonToPost.length(),"]");
		batchRequest = Arrays.asList(mapper.readValue(finalJsonToPost.toString(), RequestPojo[].class));
		logger.info("Batch Request String: "+ finalJsonToPost);
		logger.info("Batch Request Object: "+ batchRequest);
	}
	
	@Given("^User has valid data to post for Options with European Style$")
	public void UserHasValidDataToPostOptionsEuropean() throws IOException { 		
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("Options")), ValidationData.getValidDataForOptionsWithEuropeanStyle());	
	}
	
	@Given("^User has valid data to post for Options with American Style$")
	public void UserHasValidDataToPostOptionsAmerican() throws IOException { 		
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getValidDataForOptionsWithEuropeanStyle());	
	}
		
	@When("^User posts the json request$")
	public void UserPostTheData() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    response = restApiController.executePostMethodWithRequestBodyAsString(propLoader.getValidateUrl(), jsonToPost);
	}
	
	@When("^User posts the json request as object$")
	public void UserPostTheDataAsObject() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    response = restApiController.executePostMethodWithRequestBodyAsObject(propLoader.getValidateUrl(), requestPojo);
	}
	
	@When("^User posts the json request for validate batch api as string$")
	public void UserPostTheDataForBatchAsString() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    batchResponse = restApiController.executePostMethodWithRequestBodyAsStringBatch(propLoader.getValidateBatchUrl(), finalJsonToPost.toString());
	    System.out.println("Batch Response : "+ batchResponse);
	}
	
	@When("^User posts the json request for validate batch api as object$")
	public void UserPostTheDataForBatchAsObject() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    batchResponse = restApiController.executePostMethodWithRequestBodyAsListOfObject(propLoader.getValidateBatchUrl(), batchRequest);
	}
	
	@Then("^Verify the response is valid$")
	public void verifyResponse(){
	    Assert.assertEquals("SUCCESS",response.getStatus());
	    Assert.assertNull(response.getMessages());
	}
	
	@Then("^Verify the batch response is valid$")
	public void verifyBatchResponse(){
		batchResponse.stream().forEach(response->{
	    	Assert.assertEquals("SUCCESS",response.getStatus());
	    	Assert.assertNull(response.getMessages());
		});
	}
	
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
	
	@Then("Verify the response contains the {string} error message")
	public void responseContainsTheRequiredErrorMessage(String messages) throws Throwable {
		String msg="";
		for(String message : messages.split("\\|")) {
			msg = message.trim();
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
	   }
	
	@Given("User has Value Date older than Trade Date for product type {string}")
	public void UserHasInvalidValueDate(String type) throws IOException {	
	    requestPojo = mapper.readValue(JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getValueDateOlderThanTradeDateData()),RequestPojo.class);	
	}
	
	@Then("^Verify the response contains correct error message when value date is before trade date$")
	public void verifyResponseContainsCorrectErrorMessageValueDateBeforeTradeDate() {
		Assert.assertEquals("Value date "+requestPojo.getValueDate()+" cannot be null and it has to be after trade date "+requestPojo.getTradeDate(),response.getMessages().get(0).trim());
	}
	
	@Given("User has the Value Date on weekend for product type {string}")
	public void UserHasWeekendValueDate(String type) throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getWeekendValueDateData());	
	}
	
	@Given("User has Invalid counterparty for product type {string}")
	public void UserHasInvalidCustomerName(String type) throws IOException {	
		 requestPojo = mapper.readValue(JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getInvalidCounterpartyData()),RequestPojo.class);	
	}
	
	@Then("^Verify the response contains correct error message when Invalid Customer is provided$")
	public void verifyResponseContainsCorrectErrorMessageForInvalidCounterparty() {
		Assert.assertEquals("Counterparty ["+requestPojo.getCustomer()+"] is not supported. Supported counterparties: [[PLUTO2, PLUTO1]]",response.getMessages().get(0).trim());
	}
	
	@Given("User has Invalid Legal entity for product type {string}")
	public void UserHasInvalidLE(String type) throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getInvalidLegalEntityData());	
	}
	
	@Given("^User has Invalid Product Type in input$")
	public void UserHasInvalidPrdtType() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("Spot")), ValidationData.getInvalidProductTypeData());	
	}
	
	@Given("^User has Invalid Style in input$")
	public void UserHasInvalidStyle() throws IOException {	
	    requestPojo = mapper.readValue(JsonUtils.getJsonToPost(new File(getJsonFilePath("Options")), ValidationData.getInvalidStyleData()),RequestPojo.class);	
	}
	
	@Given("User has Invalid ISO Currency for product type {string}")
	public void UserHasInvalidISOCurrency(String type) throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getInvalidCurrencyPairData());	
	}
	
	@Given("^User has data having Expiry Date after Delivery Date$")
	public void UserHasInValidDataExpiryDateAfterDeliveryDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getExpiryDateAfterDeliveryDateInvalidData());	
	}
	
	@Given("^User has data having Premium Date after Delivery Date$")
	public void UserHasInValidDataPremiumDateAfterDeliveryDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getPremiumDateAfterDeliveryDateInvalidData());
	    requestPojo = mapper.readValue(jsonToPost, RequestPojo.class);
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
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getExerciseStartDateBeforeTradeDateInvalidData());	
	}
	
	@Given("^User has data having Exercise Start Date after Expiry Date$")
	public void UserHasInValidDataExerciseStartDateAfterExpiryDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getExerciseStartDateAfterExpiryDateInvalidData());	
	}
	
	@Given("^User has data with American Style and without Excercise Start Date$")
	public void UserHasAmericanStyleWithoutExerciseStartDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("Options")), ValidationData.getAmericanStyleWithoutStartExerciseDateData());	
	}
	
	@Given("^User has data with European Style and with Excercise Start Date$")
	public void UserHasEuropeanStyleWithExerciseStartDate() throws IOException {	
	    requestPojo = mapper.readValue(JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getEuropeanStyleWithStartExerciseDateData()),RequestPojo.class);	
	}
	
	@Given("^User has amount1 and amount2 as zero , Premium is zero and PayCcy is empty in input data$")
	public void UserHasInvalidAmountPayCcyPremium() throws IOException {	
	    requestPojo = mapper.readValue(JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getInvalidAmountsInvalidPayCcyInvalidPremium()),RequestPojo.class);	
	}
	
	@Given("User has amount1 and amount2 as zero , Rate is zero and Trader is empty in input data for product type {string}")
	public void UserHasInvalidAmountRateTrader(String type) throws IOException {	
	    requestPojo = mapper.readValue(JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getInvalidAmountsInvalidRateInvalidTrader()),RequestPojo.class);	
	}
	
	public String getJsonFilePath(String type) {
		if(type.equalsIgnoreCase("Trade"))  return Constants.JSON_FILE_LOCATION_TRADE;
		else if(type.equalsIgnoreCase("Spot")) return Constants.JSON_FILE_LOCATION_SPOT;
		else if(type.equalsIgnoreCase("Forward")) return Constants.JSON_FILE_LOCATION_FORWARD;
		else if(type.equalsIgnoreCase("Options")) return Constants.JSON_FILE_LOCATION_OPTIONS_WITHOUT_EXERCISE_START_DATE;
		else if(type.equalsIgnoreCase("OptionsWithExerciseDate")) return Constants.JSON_FILE_LOCATION_OPTIONS_WITH_EXERCISE_START_DATE;
		else if(type.equalsIgnoreCase("Batch")) return Constants.JSON_FILE_LOCATION_BATCH_ALL_PRODUCT_TYPE;
		return null;
	}
}