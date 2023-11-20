package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

public class FakerData {

	
	
	
	public static String GenerateName()
	{
		Faker faker = new Faker();
//		return faker.regexify("[A-Za-z0-9 ,_-]{10}");
		return faker.regexify("[A-Z]{10}");
	}

	public static String GenerateDescription()
	{
		Faker faker = new Faker();
//		return faker.regexify("[A-Za-z0-9 ,_!@#$%^&*(-]{13}");
		return faker.regexify("[A-Z]{13}");
	}
}
