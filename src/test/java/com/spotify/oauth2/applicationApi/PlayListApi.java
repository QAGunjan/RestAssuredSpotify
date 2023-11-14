package com.spotify.oauth2.applicationApi;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import com.spotify.oauth2.utils.Generic;
import com.spotify.oauth2.utils.Routes;
import com.spotify.oauth2.utils.SpecificationsBuilder;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PlayListApi {

	
	public static Response post(Playlist requestplaylist) throws IOException {
		return Generic.post(Routes.USERS + "/" + ConfigLoader.getInstance().getUserID() + Routes.PLAYLISTS,
				requestplaylist);
	}

	public static Response get(String PlayList_ID) {
		return Generic.get(Routes.PLAYLISTS + "/" + PlayList_ID);

	}

}
