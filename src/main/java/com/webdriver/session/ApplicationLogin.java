package com.webdriver.session;

import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpStatus;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;

public class ApplicationLogin {
	
	private String applicationUrl;
	private String requestBody;
	
	public ApplicationLogin withApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
		return this;
	}
	
	public ApplicationLogin withRequestBody(String requestBody) {
		this.requestBody = requestBody;
		return this;
	}
	
	public Cookies sendRequest() throws URISyntaxException {
		return given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.body(requestBody)
		.when()
		.post(new URI(applicationUrl))
		.then()
		.statusCode(HttpStatus.SC_OK)
		.and()
		.extract()
		.detailedCookies();
	}

}
