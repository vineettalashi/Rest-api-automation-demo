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
import com.qe.vt.utils.JacksonUtils;

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
	    jsonToPost = JacksonUtils.getJsonToPost(new File(Constants.JSON_FILE_LOCATION_TRADE), ValidationData.getValidData());	
	}
	
	@When("^User posts the json data$")
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
	
	@Given("^User has Value Date older than Trade Date$")
	public void UserHasInvalidValueDate() throws IOException {	
	    jsonToPost = JacksonUtils.getJsonToPost(new File(Constants.JSON_FILE_LOCATION_TRADE), ValidationData.getValueDateOlderThanTradeDateData());	
	}
	
	@Then("^Verify the response contains the required error message$")
	public void aResponseContainsTheRequiredErrorMessage() throws Throwable {
		Assert.assertEquals("ERROR",response.getStatus());
	    Assert.assertTrue(response.getMessages().get(0).contains("cannot be null and it has to be after trade date"));
	}
}