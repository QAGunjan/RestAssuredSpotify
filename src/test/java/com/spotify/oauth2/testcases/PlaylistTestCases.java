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
import com.spotify.oauth2.pojo.ErrorRoot;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.FakerData;
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

	public Playlist requestplaylist = new Playlist();
	public Playlist responsePlayList;
	public ErrorRoot requestError;
	public ErrorRoot responseError;
	private int randomInt;

	public Playlist SetUp(String ID, String Name, Response response) {
		responsePlayList = response.as(Playlist.class);

		responsePlayList.setId(response.path(ID));
		responsePlayList.setName(response.path(Name));

		return responsePlayList;
	}

	@Description("This is for creating the PlayList")
	@Story("Create a playlist story")
	@Test(description = "ValidateCreatingAPlaylist")
	public void ValidateCreatingAPlaylist() throws IOException {

//		playlistBuilder(DataLoader.getInstance().getPlaylistName(), DataLoader.getInstance().getPlaylistDescription(),
//				false);
		
		playlistBuilder(FakerData.GenerateName(), FakerData.GenerateDescription(),
				false);
		Response response = PlayListApi.post(requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_201);
		SetUp("id", "name", response);
		assertPlaylist(responsePlayList, requestplaylist);

	}

	@Description("This is for creating the PlayList without the PlayList name")
	@Story("Create a playlist story")
	@Test(description = "ValidateCreatingAPlaylistWithoutPlayListName")
	public void ValidateCreatingAPlaylistWithoutPlayListName() throws IOException {
		playlistBuilder("", FakerData.GenerateDescription(), false);
		Response response = PlayListApi.post(requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_400);

		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 400, "Missing required field: name");

	}

	@Description("This is for creating the PlayList without the PlayList Description")
	@Story("Create a playlist story")
	@Test(description = "ValidateCreatingAPlaylistWithoutPlayListDescription")
	public void ValidateCreatingAPlaylistWithoutPlayListDescription() throws IOException {
		playlistBuilder(FakerData.GenerateName(), "", false);
		Response response = PlayListApi.post(requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_201);
		responsePlayList = response.as(Playlist.class);
		assertPlaylist(responsePlayList, requestplaylist);

	}

	@Description("This is for fetching the PlayList")
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

//		Random randomGenerator = new Random();
//		randomInt = randomGenerator.nextInt(100);
//
//		if (!name.isEmpty()) {
//			requestplaylist.setName(name + randomInt);
//		}
//
//		else {
//			requestplaylist.setName(name);
//
//		}
//
//		if (!description.isEmpty()) {
//			requestplaylist.setDescription(description + randomInt);
//		} else {
//			requestplaylist.setDescription(description);
//		}

		requestplaylist.setName(name);
		requestplaylist.setDescription(description);
		requestplaylist.set_public(_public);

		return requestplaylist;
	}

	public void assertStatusCode(int ActualStatusCode, StatusCodes statuscode) {
		assertThat(ActualStatusCode, equalTo(statuscode.code));
	}

	public void assertError(ErrorRoot responseError, int ExpectedStatusCode, String ExpectedMessage) {
		assertThat(responseError.getError().getStatus(), equalTo(ExpectedStatusCode));
		assertThat(responseError.getError().getMessage(), equalTo(ExpectedMessage));
	}

	public void assertPlaylist(Playlist responsePlayList, Playlist requestplaylist) {
		assertThat(responsePlayList.getName(), equalTo(requestplaylist.getName()));
		assertThat(responsePlayList.getDescription(), equalTo(requestplaylist.getDescription()));
	}

}
