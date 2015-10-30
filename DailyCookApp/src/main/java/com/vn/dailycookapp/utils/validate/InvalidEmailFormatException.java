package com.vn.dailycookapp.utils.validate;

import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;


public class InvalidEmailFormatException extends DCAException {
	
	private final ErrorCodeConstant	error				= ErrorCodeConstant.INVALID_EMAIL;
	private static final long		serialVersionUID	= 1L;
	
	public InvalidEmailFormatException() {
		setDescription(error.getMessage());
		setErrorCode(error.getErrorCode());
	}
}
