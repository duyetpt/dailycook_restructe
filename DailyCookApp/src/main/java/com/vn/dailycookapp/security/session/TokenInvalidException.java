package com.vn.dailycookapp.security.session;

public class TokenInvalidException extends Exception {
	private static final long	serialVersionUID	= 2369396243195235378L;
	private int					errorCode;
	
	public TokenInvalidException(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
}
