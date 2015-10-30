package com.vn.dailycookapp.security.authorization;

import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class AuthorizationException extends DCAException {

	private static final long	serialVersionUID	= -8393343165021453633L;
	public AuthorizationException() {
		setErrorCode(ErrorCodeConstant.INVALID_TOKEN.getErrorCode());
		setDescription(ErrorCodeConstant.INVALID_TOKEN.getMessage());
	}
}
