package org.dao;

import org.DCAUtilsException;

public class DAOException extends DCAUtilsException {
	
	private static final long	serialVersionUID	= -34994879673744419L;
	
	public DAOException() {
		this.errorCode = -1100;
		this.msg = "dao exception";
	}
}
