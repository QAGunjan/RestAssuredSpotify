package com.spotify.oauth2.applicationApi;

import java.io.IOException;

import com.spotify.oauth2.pojo.FetchTrack;
import com.spotify.oauth2.utils.Generic;
import com.spotify.oauth2.utils.Routes;
import com.spotify.oauth2.utils.TokenManager;

import io.restassured.response.Response;

public class TrackApi {
	
	public static Response get(String PlayList_ID) throws IOException
	{
		return Generic.get(Routes.PLAYLISTS + "/" + PlayList_ID + Routes.TRACKS, TokenManager.getToken());
	}
	
	public static Response get() throws IOException
	{
		return Generic.get(Routes.TRACKS + "/" + Routes.Track_ID , TokenManager.getToken());
	}

}
