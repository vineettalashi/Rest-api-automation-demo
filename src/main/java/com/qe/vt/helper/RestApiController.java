package com.qe.vt.helper;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qe.vt.pojo.RequestPojo;
import com.qe.vt.pojo.ResponsePojo;

import io.restassured.response.Response;

public class RestApiController {
	Logger logger = LoggerFactory.getLogger(RestApiController.class);
	
	 private RequestSpecificationBuilder restSpecBuilder;

	    public RestApiController(RequestSpecificationBuilder restSpecBuilder) {
	        this.restSpecBuilder = restSpecBuilder;
	    }

	    public Response executeRequestWithGetMethod(String resourceUrl) {
	        Response response = restSpecBuilder.getRequestSpecification().get(resourceUrl);
	        return response;
	    }

	    public ResponsePojo executePostMethodWithRequestBodyAsString(String resource, String requestBody) {
	    	ResponsePojo response = restSpecBuilder.getRequestSpecification().body(requestBody).post(resource).as(ResponsePojo.class);
	        return response;
	    }
	    
	    public ResponsePojo executePostMethodWithRequestBodyAsObject(String resource, RequestPojo requestBody) {
	    	ResponsePojo response = restSpecBuilder.getRequestSpecification().body(requestBody).post(resource).as(ResponsePojo.class);
	        return response;
	    }
	    
	    public List<ResponsePojo> executePostMethodWithRequestBodyAsStringBatch(String resource, String requestBody) {
	    	Response resp = restSpecBuilder.getRequestSpecification().body(requestBody).post(resource);
	    	logger.info("Response"+resp.asPrettyString());
	    	List<ResponsePojo> listOfResponse = Arrays.asList(resp.as(ResponsePojo[].class));
	        return listOfResponse;
	    }
	    
	    public List<ResponsePojo> executePostMethodWithRequestBodyAsListOfObject(String resource, List<RequestPojo> requestBody) {
	    	//List<ResponsePojo> listOfResponse = Arrays.asList(restSpecBuilder.getRequestSpecification().body(requestBody).post(resource).as(ResponsePojo[].class));
	    	//List<ResponsePojo> listOfResponse = restSpecBuilder.getRequestSpecification().body(requestBody).post(resource).jsonPath().getList("", ResponsePojo.class);
	      //  return listOfResponse;
	        Response resp = restSpecBuilder.getRequestSpecification().body(requestBody).post(resource);
	    	logger.info("Response"+resp.asPrettyString());
	    	List<ResponsePojo> listOfResponse = Arrays.asList(resp.as(ResponsePojo[].class));
	        return listOfResponse;
	    }

}
