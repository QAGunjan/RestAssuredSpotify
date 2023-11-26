
package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter

public class Track {

	@JsonProperty("uri")
	private String uri;

	public static Track SetUp(String uri) {
		Track requestTrack = new Track();
		requestTrack.setUri(uri);

		return requestTrack;
	}

}
