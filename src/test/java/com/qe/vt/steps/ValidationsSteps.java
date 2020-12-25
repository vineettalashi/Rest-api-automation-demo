package com.qe.vt.steps;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;

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
	RequestSpecificationBuilder reqstSpecBuilder;
	RequestPojo requestPojo;
	ResponsePojo response;
	String jsonToPost;
	String jsonFile;
	PropertiesLoader propLoader = PropertiesLoader.getInstance();
	
	@Given("User has valid data to post for {string}")
	public void UserHasValidDataToPostPrdtType(String type) throws IOException { 		
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getValidDataForTradeSpotForward());	
	}
	
	@Given("^User has valid data to post for Options with European Style$")
	public void UserHasValidDataToPostOptionsEuropean() throws IOException { 		
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("Options")), ValidationData.getValidDataForOptionsWithEuropeanStyle());	
	}
	
	@Given("^User has valid data to post for Options with American Style$")
	public void UserHasValidDataToPostOptionsAmerican() throws IOException { 		
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("Options")), ValidationData.getValidDataForOptionsWithEuropeanStyle());	
	}
		
	@When("^User posts the json request$")
	public void UserPostTheData() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder().setBaseUrl(propLoader.getBaseUrl()).setContentTypeJson().setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    response = restApiController.executePostMethodWithRequestBody(propLoader.getValidateUrl(), jsonToPost);
	}
	
	@Then("^Verify the response is valid$")
	public void verifyResponse() throws Throwable {
	    Assert.assertEquals("SUCCESS",response.getStatus());
	    Assert.assertNull(response.getMessages());
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
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getValueDateOlderThanTradeDateData());	
	}
	
	@Given("User has the Value Date on weekend for product type {string}")
	public void UserHasWeekendValueDate(String type) throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getWeekendValueDateData());	
	}
	
	@Given("User has Invalid counterparty for product type {string}")
	public void UserHasInvalidCustomerName(String type) throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath(type)), ValidationData.getInvalidCounterpartyData());	
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
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("Options")), ValidationData.getInvalidStyleData());	
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
	}
	
	@Given("^User has data having Exercise Start Date before Trade Date$")
	public void UserHasInValidDataExerciseStartDateBeforeTradeDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getExerciseStartDateBeforeTradeDateInvalidData());	
	}
	
	@Given("^User has data having Exercise Start Date after Expiry Date$")
	public void UserHasInValidDataExerciseStartDateAfterExpiryDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(getJsonFilePath("OptionsWithExerciseDate")), ValidationData.getExerciseStartDateAfterExpiryDateInvalidData());	
	}
	
	public String getJsonFilePath(String type) {
		if(type.equalsIgnoreCase("Trade"))  return Constants.JSON_FILE_LOCATION_TRADE;
		else if(type.equalsIgnoreCase("Spot")) return Constants.JSON_FILE_LOCATION_SPOT;
		else if(type.equalsIgnoreCase("Forward")) return Constants.JSON_FILE_LOCATION_FORWARD;
		else if(type.equalsIgnoreCase("Options")) return Constants.JSON_FILE_LOCATION_OPTIONS_WITHOUT_EXERCISE_START_DATE;
		else if(type.equalsIgnoreCase("OptionsWithExerciseDate")) return Constants.JSON_FILE_LOCATION_OPTIONS_WITH_EXERCISE_START_DATE;
		return null;
	}
}