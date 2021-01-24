package com.qe.vt.apihelper;

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
	        return restSpecBuilder.getRequestSpecification().get(resourceUrl);
	    }

	    public ResponsePojo executePostMethodWithRequestBodyAsString(String resource, String requestBody) {
	    	return restSpecBuilder.getRequestSpecification().body(requestBody).post(resource).as(ResponsePojo.class);
	    }
	    
	    public ResponsePojo executePostMethodWithRequestBodyAsObject(String resource, RequestPojo requestBody) {
	    	return restSpecBuilder.getRequestSpecification().body(requestBody).post(resource).as(ResponsePojo.class);
	        
	    }
	    
	    public List<ResponsePojo> executePostMethodWithRequestBodyAsStringBatch(String resource, String requestBody) {
	    	Response resp = restSpecBuilder.getRequestSpecification().body(requestBody).post(resource);
	    	String prettyResponse = resp.asPrettyString();
	    	logger.info("Response {}",prettyResponse);
	    	return Arrays.asList(resp.as(ResponsePojo[].class));
	    }
	    
	    public List<ResponsePojo> executePostMethodWithRequestBodyAsListOfObject(String resource, List<RequestPojo> requestBody) {
	        Response resp = restSpecBuilder.getRequestSpecification().body(requestBody).post(resource);
	    	logger.info("Response {}",null!=resp?resp.asPrettyString():"Null Response");
	    	return Arrays.asList(null!=resp?resp.as(ResponsePojo[].class):null);
	    }

}
