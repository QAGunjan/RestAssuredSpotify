
package com.spotify.oauth2.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter

public class IteamRemoveToPlayList {
	
	public static IteamRemoveToPlayList removeIteamToPlaylistrequest;

	
    @JsonProperty("tracks")
    private List<Track> tracks;
    @JsonProperty("snapshot_id")
    private String snapshotId;

 
	public static IteamRemoveToPlayList playlistBuilder(String uri, String snapshotId)
	{
		List<Track> alltrack = new ArrayList<>();
		alltrack.add(Track.SetUp(uri));
		
		removeIteamToPlaylistrequest = new IteamRemoveToPlayList();
		
		removeIteamToPlaylistrequest.setTracks(alltrack);
		removeIteamToPlaylistrequest.setSnapshotId(snapshotId);
		
		return removeIteamToPlaylistrequest;
		
		
	}
	
	

}
