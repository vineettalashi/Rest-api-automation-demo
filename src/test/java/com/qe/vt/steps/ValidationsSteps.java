package com.qe.vt.steps;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;

import com.qe.vt.helper.Constants;
import com.qe.vt.helper.PropertiesLoader;
import com.qe.vt.helper.RequestSpecificationBuilder;
import com.qe.vt.helper.RestApiController;
import com.qe.vt.helper.ValidationData;
import com.qe.vt.pojo.ResponsePojo;
import com.qe.vt.utils.JsonUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValidationsSteps {
	RequestSpecificationBuilder reqstSpecBuilder;
	ResponsePojo response;
	String jsonToPost;
	PropertiesLoader propLoader = PropertiesLoader.getInstance();
	
	@Given("^User has valid data to post for happy flow$")
	public void UserHasValidDataToPost() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(Constants.JSON_FILE_LOCATION_TRADE), ValidationData.getValidData());	
	}
	
	@When("^User posts the json request$")
	public void UserPostTheData() throws Throwable {
		reqstSpecBuilder = new RequestSpecificationBuilder()
	            .setBaseUrl(PropertiesLoader.getInstance().getBaseUrl())
	            .setContentTypeJson()
	            .setAcceptasJson();
	    RestApiController restApiController = new RestApiController(reqstSpecBuilder);
	    response = restApiController.executePostMethodWithRequestBody(propLoader.getValidateUrl(), jsonToPost);
	}
	
	@Then("^Verify the response is valid$")
	public void verifyResponse() throws Throwable {
	    Assert.assertEquals("SUCCESS",response.getStatus());
	    Assert.assertNull(response.getMessages());
	}
	
	@Then("Verify the response contains the {string} error message")
	public void aResponseContainsTheRequiredErrorMessage(String string) throws Throwable {
		Assert.assertEquals("ERROR",response.getStatus());
	    Assert.assertTrue("Expected Error: "+string+"Actual Error: "+response.getMessages().get(0),response.getMessages().get(0).contains(string));
	}
	
	@Given("^User has Value Date older than Trade Date in input$")
	public void UserHasInvalidValueDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(Constants.JSON_FILE_LOCATION_TRADE), ValidationData.getValueDateOlderThanTradeDateData());	
	}
	
	@Given("^User has the Value Date on weekend in input$")
	public void UserHasWeekendValueDate() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(Constants.JSON_FILE_LOCATION_TRADE), ValidationData.getWeekendValueDateData());	
	}
	
	@Given("^User has Invalid counterparty/customer in input$")
	public void UserHasInvalidCustomerName() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(Constants.JSON_FILE_LOCATION_TRADE), ValidationData.getInvalidCounterpartyData());	
	}
	
	@Given("^User has Invalid Legal entity in input$")
	public void UserHasInvalidLE() throws IOException {	
	    jsonToPost = JsonUtils.getJsonToPost(new File(Constants.JSON_FILE_LOCATION_TRADE), ValidationData.getInvalidLegalEntityData());	
	}
}