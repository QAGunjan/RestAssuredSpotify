package com.spotify.oauth2.applicationApi;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.FetchTrack;
import com.spotify.oauth2.pojo.IteamRemoveToPlayList;
import com.spotify.oauth2.pojo.IteamToAddPlayList;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import com.spotify.oauth2.utils.Generic;
import com.spotify.oauth2.utils.Routes;
import com.spotify.oauth2.utils.SpecificationsBuilder;
import com.spotify.oauth2.utils.TokenManager;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PlayListApi {

	public static Response post(Playlist requestplaylist) throws IOException {
		return Generic.post(Routes.USERS + "/" + ConfigLoader.getInstance().getUserID() + Routes.PLAYLISTS,
				requestplaylist);
	}

//   @Step("Passing requestplaylist: {0} and acessToken: {1}")
	@Step
	public static Response post(Playlist requestplaylist, String acessToken) throws IOException {

		return Generic.post(Routes.USERS + "/" + ConfigLoader.getInstance().getUserID() + Routes.PLAYLISTS,
				requestplaylist, acessToken);

	}
	
	public static Response post(String PlayList_ID, IteamToAddPlayList addIteamToPlaylistrequest) throws IOException {

		return Generic.post(Routes.PLAYLISTS + "/" + PlayList_ID + Routes.TRACKS,
				addIteamToPlaylistrequest, TokenManager.getToken());

	}
	
	
	public static Response post(String acessToken, String PlayList_ID, IteamToAddPlayList addIteamToPlaylistrequest) throws IOException {

		return Generic.post(Routes.PLAYLISTS + "/" + PlayList_ID + Routes.TRACKS,
				addIteamToPlaylistrequest, acessToken);

	}
	
	public static Response delete(String PlayList_ID, IteamRemoveToPlayList removeIteamToPlaylistrequest) throws IOException {

		return Generic.delete(TokenManager.getToken(), Routes.PLAYLISTS + "/" + PlayList_ID + Routes.TRACKS,
				removeIteamToPlaylistrequest);

	}
	
	
	
	public static Response update(String PlayList_ID, String acessToken, Playlist requestplaylist)
	{
		return Generic.update(Routes.PLAYLISTS + "/" + PlayList_ID, acessToken, requestplaylist );
	}


	public static Response get(String PlayList_ID) {
		return Generic.get(Routes.PLAYLISTS + "/" + PlayList_ID);

	}
	
	public static Response get() throws IOException
	{
		return Generic.get(Routes.TRACKS + "/" + FetchTrack.track_id , TokenManager.getToken());
	}


	public static Response get(String PlayList_ID, String acessToken) {
		return Generic.get(Routes.PLAYLISTS + "/" + PlayList_ID, acessToken);

	}

}
