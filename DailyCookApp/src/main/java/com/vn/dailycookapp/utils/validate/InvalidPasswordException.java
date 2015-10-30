package com.vn.dailycookapp.utils.validate;

import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class InvalidPasswordException extends DCAException {
	private final ErrorCodeConstant	error				= ErrorCodeConstant.INVALID_PASSWORD;
	private static final long		serialVersionUID	= 1L;
	
	public InvalidPasswordException() {
		setDescription(error.getMessage());
		setErrorCode(error.getErrorCode());
	}
}
