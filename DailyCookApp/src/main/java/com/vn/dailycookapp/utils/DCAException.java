package com.vn.dailycookapp.utils;

public class DCAException extends Exception {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private int					errorCode;
	private String				description;
	
	public int getErrorCode() {
		return errorCode;
	}
	
	protected void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getDescription() {
		return description;
	}
	
	protected void setDescription(String description) {
		this.description = description;
	}
	
}
