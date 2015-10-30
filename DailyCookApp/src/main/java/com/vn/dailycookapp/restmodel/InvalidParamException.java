package com.vn.dailycookapp.restmodel;

import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class InvalidParamException extends DCAException {
	
	private static final long		serialVersionUID	= 1L;
	private final ErrorCodeConstant	error				= ErrorCodeConstant.INVALID_DATA;
	
	public InvalidParamException() {
		setDescription(error.getMessage());
		setErrorCode(error.getErrorCode());
	}
}
