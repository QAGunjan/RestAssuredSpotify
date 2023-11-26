
package com.spotify.oauth2.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter

public class IteamToAddPlayList {

	public static IteamToAddPlayList addIteamToPlaylistrequest;
	public static IteamToAddPlayList responsePlayList;

	@JsonProperty("uris")
	private List<String> uris;
	@JsonProperty("position")
	private Integer position;
	@JsonProperty("snapshot_id")
	private String snapshot_id;
	

	public static IteamToAddPlayList playlistBuilder(List<String> uris, Integer position) {

		addIteamToPlaylistrequest = new IteamToAddPlayList();
		addIteamToPlaylistrequest.setUris(uris);
		addIteamToPlaylistrequest.setPosition(position);

		return addIteamToPlaylistrequest;

	}
	
	public static IteamToAddPlayList SetUp(String snapshot_id, Response response)
	{
		 responsePlayList = response.as(IteamToAddPlayList.class);
		 responsePlayList.setSnapshot_id(response.path(snapshot_id));
		 
		 return responsePlayList;
	}
	
	

}
