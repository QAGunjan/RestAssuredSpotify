package com.spotify.oauth2.utils;

import java.io.File;
import java.util.HashMap;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecificationsBuilder {

//	private static String acessToken = "BQB374pICbuKruAwpCtXri73WPyjBaCqV7pq_cE-jK8Cu-TcQYEGam0ELt2ibpdJF66wnU5UfZKos1-Tk0bqzKiks3wq0NY3O2er84UwYolR6pMIR56fOGVQgsk4vxPPe90RHBefJnFcbMp12epch9JgGN-ohayZK5kLkfp1t_yTdupYmj8rgBiBGEpQnXNDVjlsEFDMXmUQtp3QQUxerXCny9aTBj-aL9mkDqpTwbyK3Lqv9bzeqIRswxTqoW4ITzMFfAWPGN4hF5es";

	public static RequestSpecification getRequestSpec() {

		// for printing the details in the external file ( but in project)
//		PrintStream printOutPut= new PrintStream(new File("RestAssured.log"));

		return new RequestSpecBuilder()
				.setBaseUri("https://api.spotify.com")
				.setBasePath(Routes.BASE_PATH)
//				.addHeader("Authorization", "Bearer " + TokenManager.getToken())
				.log(LogDetail.ALL)
				.build();

//				// for logging the all request related things
//				.addFilter(new RequestLoggingFilter(LogDetail.ALL));
//				// for logging the all response related things
//
//				.addFilter(new ResponseLoggingFilter(LogDetail.ALL));

//		RestAssured.requestSpecification = reqspecbuilder.build();

	}
	
	public static RequestSpecification getAccountRequestSpec()
	{
		
		return new RequestSpecBuilder()
				.setBaseUri("https://accounts.spotify.com")
				.setContentType(ContentType.URLENC)
				.log(LogDetail.ALL)
				.build();
	}
	

	public static ResponseSpecification getResponseSpec() {
		return new ResponseSpecBuilder()
				.expectContentType(ContentType.JSON)
				.log(LogDetail.ALL)
				.build();

	}
	
	public static ResponseSpecification getResponseSpecWithNoExpectedContentType() {
		return new ResponseSpecBuilder()
				.log(LogDetail.ALL)
				.build();

	}
	


}
