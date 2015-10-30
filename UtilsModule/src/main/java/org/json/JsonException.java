package org.json;

public class JsonException extends Exception {
	
	private static final long serialVersionUID = -5201127311159929852L;
	private String message;
	private int errorCode;
	
	public JsonException(String message, int errorCode) {
		super();
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
