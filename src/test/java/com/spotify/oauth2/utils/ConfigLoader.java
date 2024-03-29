package com.spotify.oauth2.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

	public final Properties prop;
	public static ConfigLoader configloader;

	private ConfigLoader() throws IOException {
		prop = ConfigProperties
				.readConfigData("D:\\RestAssured\\RestAssuredSpotify\\src\\test\\resources\\PropertiesFiles\\config.properties");
		
		
	}

	public static ConfigLoader getInstance() throws IOException {
		if (configloader == null) {
			configloader = new ConfigLoader();
		}

		return configloader;
	}

	public String getGrantType() {
		return prop.getProperty("grant_type");

	}

	public String getRefreshToken() {
		return prop.getProperty("refresh_token");

	}

	public String getClientID() {
		return prop.getProperty("client_id");

	}

	public String getClientSecret() {
		return prop.getProperty("client_secret");

	}

	public String getUserID() {
		return prop.getProperty("user_id");

	}
	
	public String getTrackID() {
		return prop.getProperty("track_id");

	}

}
