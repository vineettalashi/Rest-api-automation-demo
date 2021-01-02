package com.qe.vt.apihelper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
        requestSpecification.contentType(ContentType.JSON);
        return this;
    }

    public RequestSpecificationBuilder setAcceptasJson() {
        requestSpecification.accept(ContentType.JSON);
        return this;
    }
}
