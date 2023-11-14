package com.spotify.oauth2.utils;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spotify.oauth2.applicationApi.PlayListApi;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.SpecificationsBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map.Entry;

public class TokenManager {

	private static String access_token;
	private static Instant expiry_time;

	public static String getToken() {

		try {

			if (access_token == null || Instant.now().isAfter(expiry_time)) {
				System.out.println("Renewing token --");
				Response response = renewToken();
				access_token = response.path("access_token");
				int expiryDurationInSeconds = response.path("expires_in");

				expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 100);
				
			}

			else {
				System.out.println("Token is good to go --");
			}
		} catch (Exception e) {
			throw new RuntimeException("ABORT!! Failed to get token -- ");
		}

		return access_token;

	}

	private static Response renewToken() throws IOException {
		HashMap<String, String> formparams = new HashMap<String, String>();
		formparams.put("grant_type", ConfigLoader.getInstance().getGrantType());
		formparams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
		formparams.put("client_id", ConfigLoader.getInstance().getClientID());
		formparams.put("client_secret", ConfigLoader.getInstance().getClientSecret());

		
		Response response = Generic.postAccount(formparams, Routes.API + Routes.TOKEN);

		if (response.statusCode() != 200) {
			throw new RuntimeException("ABORT!! Renew Token Failed -- ");
		}

		return response;

	}

}
