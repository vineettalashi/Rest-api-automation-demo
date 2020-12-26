package com.qe.vt.helper;

import javax.ws.rs.core.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationBuilder {

	RequestSpecification requestSpecification;

    public RequestSpecificationBuilder() {
        requestSpecification = RestAssured.given().log().all();
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public RequestSpecificationBuilder setBaseUrl(String url) {
        requestSpecification.baseUri(url);
        return this;
    }

    public RequestSpecificationBuilder setContentTypeJson() {
        requestSpecification.contentType(MediaType.APPLICATION_JSON);
        return this;
    }

    public RequestSpecificationBuilder setAcceptasJson() {
        requestSpecification.accept(MediaType.APPLICATION_JSON);
        return this;
    }
}
