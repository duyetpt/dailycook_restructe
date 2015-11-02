package com.vn.dailycookapp.utils;

import org.json.JsonProperty;

public enum ErrorCodeConstant {

	SUCCESSUL(0, "Ok"),
	UNKNOW_ERROR(-1, "External server is error"),
	
	INVALID_EMAIL(-100, "Email invalid"),
	INVALID_PASSWORD(-101, "invalid password"),
	LOGIN_FB_FAIL(-102, "Login in facebook fail"),
	USER_NOT_FOUND(-103, "not found user in db"),
	PASSWORD_INCORRECT(-104, "Password is incorrect"),
	USERNAME_INCORRECT(-105, "User name is incorrect"),
	//-107 existed account in uitl module
	
	INVALID_DATA(-203, "Incorrect param"),
	INVALID_TOKEN(-201, "token is invalid"),
	CLOSED_SESSION(-202, "Session is closed"),
	
	DAO_EXCEPTION(-1100, "DAO exception"),
	
	ENCRYPT_EXCEPTION(-10000, "Encrypt data excetpiton");
	
	@JsonProperty("error_code")
	private int errorCode;
	
	@JsonProperty("msg")
	private String message;

	private ErrorCodeConstant(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

}
