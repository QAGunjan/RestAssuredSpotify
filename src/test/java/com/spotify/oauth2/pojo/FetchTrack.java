
package com.spotify.oauth2.pojo;

import java.util.ArrayList;
import java.util.Arrays;
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

public class FetchTrack {
	
	
	public static FetchTrack responsePlayList;

    @JsonProperty("album")
    private Album album;
    @JsonProperty("artists")
    private List<Artist__1> artists;
    @JsonProperty("available_markets")
    private List<Object> availableMarkets;
    @JsonProperty("disc_number")
    private Integer discNumber;
    @JsonProperty("duration_ms")
    private Integer durationMs;
    @JsonProperty("explicit")
    private Boolean explicit;
    @JsonProperty("external_ids")
    private ExternalIds externalIds;
    @JsonProperty("external_urls")
    private ExternalUrls__3 externalUrls;
    @JsonProperty("href")
    private String href;
    @JsonProperty("id")
    private String id;
    @JsonProperty("is_local")
    private Boolean isLocal;
    @JsonProperty("name")
    private String name;
    @JsonProperty("popularity")
    private Integer popularity;
    @JsonProperty("preview_url")
    private Object previewUrl;
    @JsonProperty("track_number")
    private Integer trackNumber;
    @JsonProperty("type")
    private String type;
    @JsonProperty("uri")
    private String uri;
    

	public static FetchTrack SetUp(String URI, Response response) {
    	
		responsePlayList = response.as(FetchTrack.class);

		responsePlayList.setId(response.path(URI));

		return responsePlayList;
	}
    
    
   
}
