package org.dao;

import org.DCAUtilsException;

public class ExistedDataException extends DCAUtilsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistedDataException(String msg) {
		this.errorCode = -1111;
		this.msg = msg;
	}
}
