package com.qe.vt.helper;

import com.qe.vt.pojo.ResponsePojo;

import io.restassured.response.Response;

public class RestApiController {
	 private RequestSpecificationBuilder restSpecBuilder;

	    public RestApiController(RequestSpecificationBuilder restSpecBuilder) {
	        this.restSpecBuilder = restSpecBuilder;
	    }

	    public Response executeRequestWithGetMethod(String resourceUrl) {
	        Response response = restSpecBuilder.getRequestSpecification().get(resourceUrl);
	        return response;
	    }

	    public ResponsePojo executePostMethodWithRequestBody(String resource, String requestBody) {
	    	ResponsePojo response = restSpecBuilder.getRequestSpecification().body(requestBody).post(resource).as(ResponsePojo.class);
	    	System.out.println(response);
	        return response;
	    }

}
