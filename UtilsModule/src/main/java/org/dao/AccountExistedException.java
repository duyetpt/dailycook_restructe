package org.dao;

import org.DCAUtilsException;

public class AccountExistedException extends DCAUtilsException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountExistedException() {
		this.errorCode = -107;
		this.msg = "Account existed exception";
	}
}
