package com.spotify.oauth2.utils;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import com.spotify.oauth2.pojo.Playlist;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Generic {
	
	public static Response post(String endPoint, Object requestplaylist)
	{
		return given().spec(SpecificationsBuilder.getRequestSpec())
				.body(requestplaylist)
				.when().post(endPoint)
				.then().spec(SpecificationsBuilder.getResponseSpec())
				.extract().response();
	}

	public static Response get(String endPoint)
	{
		return given().spec(SpecificationsBuilder.getRequestSpec())
				.when().get(endPoint)
				.then().spec(SpecificationsBuilder.getResponseSpec())
				.extract().response();
	}


	public static Response postAccount(HashMap<String, String> formparams, String endPoint)
	{
		return given(SpecificationsBuilder.getAccountRequestSpec()).formParams(formparams)
				.when().post(endPoint).then()
				.spec(SpecificationsBuilder.getResponseSpec()).extract().response();
	}
}

