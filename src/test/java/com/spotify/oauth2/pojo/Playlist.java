
package com.spotify.oauth2.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter

 @JsonInclude(JsonInclude.Include.NON_NULL)

public class Playlist {
	
	
	public static Playlist requestplaylist;
	public static Playlist responsePlayList;
	
	@JsonProperty("collaborative")
	private Boolean collaborative;
	@JsonProperty("description")
	private String description;
	@JsonProperty("external_urls")
	private ExternalUrls externalUrls;
	@JsonProperty("followers")
	private Followers followers;
	@JsonProperty("href")
	private String href;
	@JsonProperty("id")
	private String id;
	@JsonProperty("images")
	private List<Object> images;
	@JsonProperty("name")
	private String name;
	@JsonProperty("owner")
	private Owner owner;
	@JsonProperty("primary_color")
	private Object primaryColor;
	@JsonProperty("public")
	private Boolean _public;
	@JsonProperty("snapshot_id")
	private String snapshotId;
	@JsonProperty("tracks")
	private Tracks tracks;
	@JsonProperty("type")
	private String type;
	@JsonProperty("uri")
	private String uri;

	
	
//	@Step("Request body has generated with name: {0}, description: {1} and _public: {2}")
	@Step
	public static Playlist playlistBuilder(String name, String description, boolean _public) {
		
	    requestplaylist = new Playlist();
		requestplaylist.setName(name);
		requestplaylist.setDescription(description);
		requestplaylist.set_public(_public);
		
		return requestplaylist;

	}
	
	
	@Step
	public static Playlist SetUp(Response response) {
		

		 responsePlayList = response.as(Playlist.class);  // de - serialization

		responsePlayList.setId(response.jsonPath().get("id"));
		
		
		return responsePlayList;
	
	}
	
	
	
	
	
}
