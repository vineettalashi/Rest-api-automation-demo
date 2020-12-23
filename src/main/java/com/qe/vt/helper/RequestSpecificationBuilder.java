package com.qe.vt.helper;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationBuilder {

	RequestSpecification requestSpecification;

    public RequestSpecificationBuilder() {
        requestSpecification = RestAssured.given().log().all();
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public RequestSpecificationBuilder setPort(int value) {
        requestSpecification.port(value);
        return this;
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

    public RequestSpecificationBuilder setParam(String key, String value) {
        requestSpecification.param(key, value);
        return this;
    }

    public RequestSpecificationBuilder setHeaders(Map<String, String> headerMap) {
        requestSpecification.headers(headerMap);
        return this;
    }

    public RequestSpecificationBuilder setCookies(Cookie cookies){
        requestSpecification.cookie(cookies);
        return this;
    }
}
