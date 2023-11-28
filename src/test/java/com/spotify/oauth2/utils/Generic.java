package com.spotify.oauth2.utils;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class Generic {

	public static Response post(String endPoint, Object requestplaylist) {
		return given().spec(SpecificationsBuilder.getRequestSpec()).body(requestplaylist)

				.when().post(endPoint).then()
				.spec(SpecificationsBuilder.getResponseSpec())
				.extract().response();
	}

	public static Response post(String endPoint, Object requestplaylist, String acessToken) {
		return given().spec(SpecificationsBuilder.getRequestSpec()).body(requestplaylist).auth().oauth2(acessToken)

				.when().post(endPoint).then().spec(SpecificationsBuilder.getResponseSpec()).extract().response();
	}
	
	public static Response postAccount(HashMap<String, String> formparams, String endPoint) {
		return given(SpecificationsBuilder.getAccountRequestSpec()).formParams(formparams).when().post(endPoint).then()
				.spec(SpecificationsBuilder.getResponseSpec()).extract().response();
	}

	public static Response get(String endPoint) {
		return given().spec(SpecificationsBuilder.getRequestSpec())

				.when().get(endPoint).then().spec(SpecificationsBuilder.getResponseSpec()).extract().response();
	}

	public static Response get(String endPoint, String acessToken) {
		return given().spec(SpecificationsBuilder.getRequestSpec()).auth().oauth2(acessToken)

				.when().get(endPoint).then().spec(SpecificationsBuilder.getResponseSpec()).extract().response();
	}

	public static Response update(String endPoint, String acessToken, Object requestplaylist )
	{
		return given().spec(SpecificationsBuilder.getRequestSpec()).auth().oauth2(acessToken)
		.body(requestplaylist)
		.when().put(endPoint)
		.then().spec(SpecificationsBuilder.getResponseSpecWithNoExpectedContentType()).extract().response();
	}
	
	
	public static Response delete(String acessToken, String endPoint, Object requestplaylist) {
		return given().spec(SpecificationsBuilder.getRequestSpec()).body(requestplaylist).auth().oauth2(acessToken)

				.when().delete(endPoint).then().spec(SpecificationsBuilder.getResponseSpec()).extract().response();
	}
	
}
