package com.spotify.oauth2.testcases;

import org.hamcrest.collection.ArrayAsIterableMatcher;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spotify.oauth2.applicationApi.PlayListApi;
import com.spotify.oauth2.applicationApi.TrackApi;
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
import com.spotify.oauth2.utils.StatusCode;
import com.spotify.oauth2.utils.StatusMessage;
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
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
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
		assertStatusCode(response.statusCode(), StatusCode.Status_201);
		Playlist.SetUp(response);
		assertPlaylist(Playlist.responsePlayList, Playlist.requestplaylist);
		assertThat(Playlist.responsePlayList.getOwner().getDisplayName(), equalTo("Soniya Aggarwal"));
		assertThat(Playlist.responsePlayList.getId(), is(not(emptyString())));
		assertThat(Playlist.responsePlayList.getOwner().getType(), equalTo("user"));
		assertThat(response.getHeader("cache-control"), equalTo("private, max-age=0"));
		assertThat(response.getHeader("content-encoding"), equalTo("gzip"));
		assertThat(response.getHeader("strict-transport-security"), equalTo("max-age=31536000"));
	}

	@Description("This is for fetching the PlayList")
	@Story("Fetching a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateFetchingAPlaylist")
	public void ValidateFetchingAPlaylist() throws IOException {

		Response response = PlayListApi.get(Playlist.responsePlayList.getId(), TokenManager.getToken());
		assertStatusCode(response.statusCode(), StatusCode.Status_200);
		assertPlaylist(response.as(Playlist.class), Playlist.requestplaylist);

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateUpdatingAPlaylist")
	public void ValidateUpdatingAPlaylist() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), TokenManager.getToken(),
				Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCode.Status_200);

	}

	@Description("This is for creating the PlayList without the PlayList name")
	@Story("Create a playlist story")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidateCreatingAPlaylistWithoutPlayListName")
	public void ValidateCreatingAPlaylistWithoutPlayListName() throws IOException {

		Playlist.playlistBuilder("", FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.post(Playlist.requestplaylist, TokenManager.getToken());
		assertStatusCode(response.statusCode(), StatusCode.Status_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_400, StatusMessage.CreatingWithoutPlayListName);

	}

	@Description("This is for creating the PlayList without the PlayList Description")
	@Story("Create a playlist story")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidateCreatingAPlaylistWithoutPlayListDescription")
	public void ValidateCreatingAPlaylistWithoutPlayListDescription() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), "", false);
		Response response = PlayListApi.post(Playlist.requestplaylist, TokenManager.getToken());
		assertStatusCode(response.statusCode(), StatusCode.Status_201);
		assertPlaylist(response.as(Playlist.class), Playlist.requestplaylist);

	}

	@Description("This is for creating the PlayList with invalid token")
	@Story("Create a playlist story")
	@Severity(SeverityLevel.MINOR)
	@Test(description = "ValidateCreatingAPlaylistWithInvalidToken")
	public void ValidateCreatingAPlaylistWithInvalidToken() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.post(Playlist.requestplaylist, FakerData.GenerateInvalidAcessToken());
		assertStatusCode(response.statusCode(), StatusCode.Status_401);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_401, StatusMessage.PlaylistWithInvalidToken);

	}

	@Description("This is for fetching the PlayList")
	@Story("Fetching a playlist story")
	@Severity(SeverityLevel.MINOR)
	@Test(description = "ValidateFetchingAPlaylistWithInvalidID")
	public void ValidateFetchingAPlaylistWithInvalidID() throws IOException {

		Response response = PlayListApi.get(FakerData.GenerateInvalidPlayListID(), TokenManager.getToken());
		assertStatusCode(response.statusCode(), StatusCode.Status_404);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_404, StatusMessage.PlaylistWithInvalidID);

	}

	@Description("This is for fetching the PlayList")
	@Story("Fetching a playlist story")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidateFetchingAPlaylistWithInvalidToken")
	public void ValidateFetchingAPlaylistWithInvalidToken() throws IOException {

		Response response = PlayListApi.get(Playlist.responsePlayList.getId(), FakerData.GenerateInvalidAcessToken());
		assertStatusCode(response.statusCode(), StatusCode.Status_401);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_401, StatusMessage.PlaylistWithInvalidToken);

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidateUpdatingAPlaylistWithoutPlayListName")
	public void ValidateUpdatingAPlaylistWithoutPlayListName() throws IOException {

		Playlist.playlistBuilder("", FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), TokenManager.getToken(),
				Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCode.Status_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_400, StatusMessage.UpdatingAPlaylistWithoutPlayListName);

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.MINOR)
	@Test(description = "ValidateUpdatingAPlaylistWithoutPlayListDescription")
	public void ValidateUpdatingAPlaylistWithoutPlayListDescription() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), "", false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), TokenManager.getToken(),
				Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCode.Status_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_400, StatusMessage.UpdatingAPlaylistWithoutPlayListDescription);

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidateUpdatingAPlaylistWithoutPlayListNameAndWithoutPlayListDescription")
	public void ValidateUpdatingAPlaylistWithoutPlayListNameAndWithoutPlayListDescription() throws IOException {

		Playlist.playlistBuilder("", "", false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), TokenManager.getToken(),
				Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCode.Status_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_400, StatusMessage.UpdatingAPlaylistWithoutPlayListDescription);

	}

	@Description("This is for updating the PlayList")
	@Story("Updating a playlist story")
	@Severity(SeverityLevel.MINOR)
	@Test(description = "ValidateUpdatingAPlaylistWithInvalidToken")
	public void ValidateUpdatingAPlaylistWithInvalidToken() throws IOException {

		Playlist.playlistBuilder(FakerData.GeneratePlayListName(), FakerData.GeneratePlayListDescription(), false);
		Response response = PlayListApi.update(Playlist.responsePlayList.getId(), FakerData.GenerateInvalidAcessToken(),
				Playlist.requestplaylist);
		assertStatusCode(response.statusCode(), StatusCode.Status_401);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_401, StatusMessage.PlaylistWithInvalidToken);

	}

	@Description("This is for adding iteams to the PlayList")
	@Story("adding iteams to PlayList story")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "ValidateToAddIteamToPlayList")
	public void ValidateToAddIteamToPlayList() throws IOException {

		Response response = TrackApi.get();
		FetchTrack.SetUp("uri", response);
		response = PlayListApi.post(Playlist.responsePlayList.getId(),
				IteamToAddPlayList.playlistBuilder(FetchTrack.responsePlayList.getUri(), 0));
		IteamToAddPlayList.SetUp("snapshot_id", response);
		assertStatusCode(response.statusCode(), StatusCode.Status_201);

	}

	@Description("This is for fetching iteams to the PlayList")
	@Story("fetching iteams to PlayList story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateToGetIteamToPlayList")
	public void ValidateToGetIteamToPlayList() throws IOException {

		Response response = TrackApi.get(Playlist.responsePlayList.getId());
		assertStatusCode(response.statusCode(), StatusCode.Status_200);
		assertThat(response.header("cache-control"), equalTo("public, max-age=0"));
		assertThat(response.header("Transfer-Encoding"), equalTo("chunked"));

		JSONObject jo = new JSONObject(response.asString());
		String uri = jo.getJSONArray("items").getJSONObject(0).getJSONObject("track").get("uri").toString();

		assertThat(uri, equalTo(FetchTrack.responsePlayList.getUri()));

	}

	@Description("This is for remove iteams to the PlayList")
	@Story("Remove iteams to PlayList story")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "ValidateToRemoveIteamToPlayList")
	public void ValidateToRemoveIteamToPlayList() throws IOException {

		Response response = PlayListApi.delete(Playlist.responsePlayList.getId(), IteamRemoveToPlayList.playlistBuilder(
				FetchTrack.responsePlayList.getUri(), IteamToAddPlayList.responsePlayList.getSnapshot_id()));
		assertStatusCode(response.statusCode(), StatusCode.Status_200);

	}

	@Description("This is for adding iteams to the PlayList")
	@Story("adding iteams to PlayList story")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidateToAddIteamToPlayListwithInvalidPlayListID")
	public void ValidateToAddIteamToPlayListwithInvalidPlayListID() throws IOException {

		Response response = TrackApi.get();
		FetchTrack.SetUp("uri", response);
		response = PlayListApi.post("1234",
				IteamToAddPlayList.playlistBuilder(FetchTrack.responsePlayList.getUri(), 0));
		assertStatusCode(response.statusCode(), StatusCode.Status_404);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_404, StatusMessage.PlaylistWithInvalidID);

	}

	@Description("This is for adding iteams to the PlayList")
	@Story("adding iteams to PlayList story")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidateToAddIteamToPlayListWithoutUris")
	public void ValidateToAddIteamToPlayListWithoutUris() throws IOException {

		Response response = TrackApi.get();
		FetchTrack.SetUp("uri", response);
		response = PlayListApi.post(Playlist.responsePlayList.getId(), IteamToAddPlayList.playlistBuilder("", 0));
		assertStatusCode(response.statusCode(), StatusCode.Status_400);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_400, StatusMessage.AddIteamToPlayListWithoutUris);

	}

	@Description("This is for adding iteams to the PlayList")
	@Story("adding iteams to PlayList story")
	@Severity(SeverityLevel.MINOR)
	@Test(description = "ValidateToAddIteamToPlayListWithoutPosition")
	public void ValidateToAddIteamToPlayListWithInvalidToken() throws IOException {

		Response response = TrackApi.get();
		FetchTrack.SetUp("uri", response);
		response = PlayListApi.post(FakerData.GenerateInvalidAcessToken(), Playlist.responsePlayList.getId(),
				IteamToAddPlayList.playlistBuilder(FetchTrack.responsePlayList.getUri(), 0));
		assertStatusCode(response.statusCode(), StatusCode.Status_401);
		responseError = response.as(ErrorRoot.class);
		assertError(responseError, StatusCode.Status_401, StatusMessage.PlaylistWithInvalidToken);

	}

	@Description("This is for validating json schema to the PlayList_API")
	@Story("Validating the Json Scehma of PlayList API")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidatePlayListJsonSchema")
	public void ValidatePlayListJsonSchema() throws IOException {

		String JsonSchema = "./JsonSchema/PlayListAPISchema.json";

		 PlayListApi.post(Playlist.requestplaylist, JsonSchema, TokenManager.getToken());

	}
	
	@Description("This is for validating json schema to the AddIteamToPlayList_API")
	@Story("Validating the Json Scehma of AddIteamToPlayList API")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "ValidateAddIteamToPlayListJsonSchema")
	public void ValidateAddIteamToPlayListJsonSchema() throws IOException {

		String JsonSchema2 = "./JsonSchema/AddIteamToPlayListAPISchema.json";

		 PlayListApi.post(Playlist.responsePlayList.getId(), IteamToAddPlayList.addIteamToPlaylistrequest, JsonSchema2, TokenManager.getToken());

	}

//	@Step(" ActualStatusCode: {0} and statuscode: {1}")
	@Step
	public void assertStatusCode(int ActualStatusCode, int statuscode) {
		assertThat(ActualStatusCode, equalTo(statuscode));
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
