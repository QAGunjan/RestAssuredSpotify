package com.spotify.oauth2.utils;

import java.io.IOException;
import java.util.Properties;

public class DataLoader {

	public final Properties prop;
	public static DataLoader dataloader;

	private DataLoader() throws IOException {
		prop = ConfigProperties
				.readConfigData("D:\\ECLIPSE_LATEST\\RestAssuredSpotify\\src\\test\\resources\\data.properties");
	}

	public static DataLoader getInstance() throws IOException {
		if (dataloader == null) {
			dataloader = new DataLoader();
		}

		return dataloader;
	}

	public String getPlaylistName() {
		return prop.getProperty("playlist_name");

	}

	public String getPlaylistDescription() {
		return prop.getProperty("playlist_description");

	}

	public String getPlaylistID() {
		return prop.getProperty("playlist_id");

	}

}
