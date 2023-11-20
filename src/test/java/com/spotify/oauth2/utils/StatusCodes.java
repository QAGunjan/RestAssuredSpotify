package com.spotify.oauth2.utils;

import lombok.Getter;

public enum StatusCodes {

	CODE_200(200, "Success"), 
	CODE_201(201, "Created"),
	CODE_400(400, "Bad Request");

	public final int code;
	public final String message;

	StatusCodes(int code, String message) {

		this.code = code;
		this.message = message;
	}

}
