package com.spotify.oauth2.testcases;

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
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.SpecificationsBuilder;
import com.spotify.oauth2.utils.StatusCodes;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
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
import java.util.HashMap;
import java.util.Random;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlaylistTestCases {

	public Playlist requestplaylist;
	public Playlist responsePlayList;

	public Playlist SetUp(String ID, String Name, Response response) {
		responsePlayList = response.as(Playlist.class);

		responsePlayList.setId(response.path(ID));
		responsePlayList.setName(response.path(Name));

		return responsePlayList;
	}

	@Description("This the for creating the PlayList")
	@Story("Create a playlist story")
	@Test(description = "ValidateCreatingAPlaylist")
	public void ValidateCreatingAPlaylist() throws IOException {

		playlistBuilder(DataLoader.getInstance().getPlaylistName(), DataLoader.getInstance().getPlaylistDescription(),
				false);
		Response response = PlayListApi.post(requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_201);

		SetUp("id", "name", response);

//		String ID = response.path("id");
//		responsePlayList.setId(Id);

//		response.path("name");

		assertPlaylist(responsePlayList, requestplaylist);

	}

	@Story("Fetching a playlist story")
	@Test(description = "ValidateFetchingAPlaylist")
	public void ValidateFetchingAPlaylist() throws IOException {

//		playlistBuilder(DataLoader.getInstance().getPlaylistName(), 
//				DataLoader.getInstance().getPlaylistDescription(),
//				false);

//		Response response = PlayListApi.get(DataLoader.getInstance().getPlaylistID());
		Response response = PlayListApi.get(responsePlayList.getId());
		assertStatusCode(response.statusCode(), StatusCodes.CODE_200);

		Playlist responsePlayList = response.as(Playlist.class);
		assertPlaylist(responsePlayList, requestplaylist);

	}

	public Playlist playlistBuilder(String name, String description, boolean _public) {

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);

		requestplaylist = new Playlist();
		requestplaylist.setName(name + randomInt);
		requestplaylist.setDescription(description + randomInt);
		requestplaylist.set_public(_public);

		return requestplaylist;
	}

	public void assertStatusCode(int ActualStatusCode, StatusCodes statuscode) {
		assertThat(ActualStatusCode, equalTo(statuscode.code));
	}

	public void assertPlaylist(Playlist responsePlayList, Playlist requestplaylist) {
		assertThat(responsePlayList.getName(), equalTo(requestplaylist.getName()));
		assertThat(responsePlayList.getDescription(), equalTo(requestplaylist.getDescription()));
	}

}
