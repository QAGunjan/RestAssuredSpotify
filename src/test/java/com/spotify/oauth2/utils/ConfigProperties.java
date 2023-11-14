package com.spotify.oauth2.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

	public static Properties readConfigData(String FilePath) throws IOException {
		
		FileInputStream fis = new FileInputStream(FilePath);

		Properties prop = new Properties();

		prop.load(fis);

		return prop;

	}

}
