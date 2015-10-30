package com.vn.dailycookapp.security.authentication;

import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class LoginFailException extends DCAException {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public LoginFailException(ErrorCodeConstant error) {
		setDescription(error.getMessage());
		setErrorCode(error.getErrorCode());
	}
}
