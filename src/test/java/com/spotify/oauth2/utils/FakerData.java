package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

public class FakerData {

	public static String GeneratePlayListName()
	{
		Faker faker = new Faker();
//		return faker.regexify("[A-Za-z0-9 ,_-]{10}");
		return faker.regexify("[A-Z]{10}");
	}
	
	public static String GenerateInvalidPlayListID()
	{
		Faker faker = new Faker();
//		return faker.regexify("[A-Za-z0-9 ,_-]{10}");
		return faker.regexify("[0-9]{10}");
	}
	
	public static String GenerateInvalidAcessToken()
	{
		Faker faker = new Faker();
//		return faker.regexify("[A-Za-z0-9 ,_-]{10}");
		return faker.regexify("[0-9]{10}");
	}
	

	public static String GeneratePlayListDescription()
	{
		Faker faker = new Faker();
//		return faker.regexify("[A-Za-z0-9 ,_!@#$%^&*(-]{13}");
		return faker.regexify("[A-Z]{13}");
	}
}
