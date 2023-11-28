package com.spotify.oauth2.testcases;

import org.hamcrest.collection.ArrayAsIterableMatcher;
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
import com.spotify.oauth2.pojo.FetchTrack;
import com.spotify.oauth2.pojo.IteamRemoveToPlayList;
import com.spotify.oauth2.pojo.IteamToAddPlayList;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.pojo.Track;
import com.spotify.oauth2.utils.ConfigLoader;
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.FakerData;
import com.spotify.oauth2.utils.Routes;
import com.spotify.oauth2.utils.SpecificationsBuilder;
import com.spotify.oauth2.utils.StatusCodes;
import com.spotify.oauth2.utils.TokenManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlaylistTestCases {

	public ErrorRoot requestError;
	public ErrorRoot responseError;
	
	@Description("This is for creating the PlayList")
	@Story("Create a playlist story")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "ValidateCreatingAPlaylist")
	public void ValidateCreatingAPlaylist() throws IOException {
	
		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.post(Playlist.requestplaylist, TokenManager.getToken());
		System.out.println(Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_201);
		 Playlist.SetUp("id", "name", response);
		assertPlaylist(Playlist.responsePlayList, Playlist.requestplaylist);
		 
		
	}

	@Description("This is for fetching the PlayList")
	@Story("Fetching a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateFetchingAPlaylist")
	public void ValidateFetchingAPlaylist() throws IOException {

		Response response = PlayListApi.get(Playlist.responsePlayList.getId(), TokenManager.getToken());
		assertStatusCode(response.statusCode(), StatusCodes.CODE_200);
		assertPlaylist(response.as(Playlist.class), Playlist.requestplaylist);

	}

	
	
	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "ValidateUpdatingAPlaylist")
	public void ValidateUpdatingAPlaylist() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), TokenManager.getToken(), Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_200);

	}

	@Description("This is for creating the PlayList without the PlayList name")
	@Story("Create a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateCreatingAPlaylistWithoutPlayListName")
	public void ValidateCreatingAPlaylistWithoutPlayListName() throws IOException {

		Playlist.playlistBuilder("", FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.post(Playlist.requestplaylist, TokenManager.getToken());
		assertStatusCode(response.statusCode(), StatusCodes.CODE_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 400, "Missing required field: name");

	}

	@Description("This is for creating the PlayList without the PlayList Description")
	@Story("Create a playlist story")
	@Severity(SeverityLevel.MINOR)
	@Test(description = "ValidateCreatingAPlaylistWithoutPlayListDescription")
	public void ValidateCreatingAPlaylistWithoutPlayListDescription() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), "", false);
		Response response = PlayListApi.post(Playlist.requestplaylist, TokenManager.getToken());
		assertStatusCode(response.statusCode(), StatusCodes.CODE_201);
		assertPlaylist(response.as(Playlist.class), Playlist.requestplaylist);

	}

	@Description("This is for creating the PlayList with invalid token")
	@Story("Create a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateCreatingAPlaylistWithInvalidToken")
	public void ValidateCreatingAPlaylistWithInvalidToken() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.post(Playlist.requestplaylist, FakerData.GenerateInvalidAcessToken());
		assertStatusCode(response.statusCode(), StatusCodes.CODE_401);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 401, "Invalid access token");

	}

	@Description("This is for fetching the PlayList")
	@Story("Fetching a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateFetchingAPlaylistWithInvalidID")
	public void ValidateFetchingAPlaylistWithInvalidID() throws IOException {

		Response response = PlayListApi.get(FakerData.GenerateInvalidPlayListID(), TokenManager.getToken());
		assertStatusCode(response.statusCode(), StatusCodes.CODE_404);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 404, "Invalid playlist Id");

	}

	@Description("This is for fetching the PlayList")
	@Story("Fetching a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateFetchingAPlaylistWithInvalidToken")
	public void ValidateFetchingAPlaylistWithInvalidToken() throws IOException {

		Response response = PlayListApi.get(Playlist.responsePlayList.getId(), FakerData.GenerateInvalidAcessToken());
		assertStatusCode(response.statusCode(), StatusCodes.CODE_401);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 401, "Invalid access token");

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateUpdatingAPlaylistWithoutPlayListName")
	public void ValidateUpdatingAPlaylistWithoutPlayListName() throws IOException {

		Playlist.playlistBuilder("", FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), TokenManager.getToken(), Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 400, "Attribute name is empty");

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.MINOR)
	@Test(description = "ValidateUpdatingAPlaylistWithoutPlayListDescription")
	public void ValidateUpdatingAPlaylistWithoutPlayListDescription() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), "", false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), TokenManager.getToken(), Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 400, "Attribute description is empty");

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateUpdatingAPlaylistWithoutPlayListNameAndWithoutPlayListDescription")
	public void ValidateUpdatingAPlaylistWithoutPlayListNameAndWithoutPlayListDescription() throws IOException {

		Playlist.playlistBuilder("", "", false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), TokenManager.getToken(), Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 400, "Attribute description is empty");

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateUpdatingAPlaylistWithInvalidToken")
	public void ValidateUpdatingAPlaylistWithInvalidToken() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), FakerData.GenerateInvalidAcessToken(),
				Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_401);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 401, "Invalid access token");

	}

	
	@Description("This is for adding iteams to the PlayList")
	@Story("adding iteams to PlayList story")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "ValidateToAddIteamToPlayList")
	public void ValidateToAddIteamToPlayList() throws IOException {

		Response response = PlayListApi.get();
		FetchTrack.SetUp("uri", response);
		List<String> list = Arrays.asList(FetchTrack.responsePlayList.getUri());
		response = PlayListApi.post(Playlist.responsePlayList.getId(),IteamToAddPlayList.playlistBuilder(list, 0));
		IteamToAddPlayList.SetUp("snapshot_id", response);
		assertStatusCode(response.statusCode(), StatusCodes.CODE_201);

	}
	
	@Description("This is for adding iteams to the PlayList")
	@Story("adding iteams to PlayList story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateToAddIteamToPlayListwithInvalidPlayListID")
	public void ValidateToAddIteamToPlayListwithInvalidPlayListID() throws IOException {

		Response response = PlayListApi.get();
		FetchTrack.SetUp("uri", response);
		List<String> list = Arrays.asList(FetchTrack.responsePlayList.getUri());
		response = PlayListApi.post("1234",IteamToAddPlayList.playlistBuilder(list, 0));
		assertStatusCode(response.statusCode(), StatusCodes.CODE_404);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 404, "Invalid playlist Id");

	}
	
	@Description("This is for adding iteams to the PlayList")
	@Story("adding iteams to PlayList story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateToAddIteamToPlayListWithoutUris")
	public void ValidateToAddIteamToPlayListWithoutUris() throws IOException {

		
		Response response = PlayListApi.get();
		FetchTrack.SetUp("uri", response);
		List<String> list = Arrays.asList("");
		response = PlayListApi.post(Playlist.responsePlayList.getId(), IteamToAddPlayList.playlistBuilder(list, 0));
		assertStatusCode(response.statusCode(), StatusCodes.CODE_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 400, "Invalid track uri: ");

	}
	
	@Description("This is for adding iteams to the PlayList")
	@Story("adding iteams to PlayList story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateToAddIteamToPlayListWithoutPosition")
	public void ValidateToAddIteamToPlayListWithInvalidToken() throws IOException {

		
		Response response = PlayListApi.get();
		FetchTrack.SetUp("uri", response);
		List<String> list = Arrays.asList(FetchTrack.responsePlayList.getUri());
		response = PlayListApi.post(FakerData.GenerateInvalidAcessToken(), Playlist.responsePlayList.getId(), IteamToAddPlayList.playlistBuilder(list, 0));
		assertStatusCode(response.statusCode(), StatusCodes.CODE_401);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, 401, "Invalid access token");

	}
	
	
	@Description("This is for remove iteams to the PlayList")
	@Story("Remove iteams to PlayList story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateToRemoveIteamToPlayList")
	public void ValidateToRemoveIteamToPlayList() throws IOException {
		
		Response response = PlayListApi.delete(Playlist.responsePlayList.getId(), 
				IteamRemoveToPlayList.playlistBuilder(FetchTrack.responsePlayList.getUri(), 
						IteamToAddPlayList.responsePlayList.getSnapshot_id()));
		assertStatusCode(response.statusCode(), StatusCodes.CODE_200);

	}
	
	
//	@Step(" ActualStatusCode: {0} and statuscode: {1}")
	@Step
	public void assertStatusCode(int ActualStatusCode, StatusCodes statuscode) {
		assertThat(ActualStatusCode, equalTo(statuscode.code));
	}

	public void assertError(ErrorRoot responseError, int ExpectedStatusCode, String ExpectedMessage) {
		assertThat(responseError.getError().getStatus(), equalTo(ExpectedStatusCode));
		assertThat(responseError.getError().getMessage(), equalTo(ExpectedMessage));
	}

//  @Step(" responsePlayList: {0} and requestplaylist: {1}")
	@Step
	public void assertPlaylist(Playlist responsePlayList, Playlist requestplaylist) {
		assertThat(responsePlayList.getName(), equalTo(requestplaylist.getName()));
		assertThat(responsePlayList.getDescription(), equalTo(requestplaylist.getDescription()));
	}

	public void assertContentType(String ActualContentType, String ExpectedContentType) {
		assertThat(ActualContentType, equalTo(ExpectedContentType));
	}
}
