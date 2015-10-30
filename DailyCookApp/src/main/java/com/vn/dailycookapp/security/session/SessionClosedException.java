package com.vn.dailycookapp.security.session;

public class SessionClosedException extends Exception {

	private static final long	serialVersionUID	= -6357126558798585006L;
	private int					errorCode;
	
	public SessionClosedException(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}
