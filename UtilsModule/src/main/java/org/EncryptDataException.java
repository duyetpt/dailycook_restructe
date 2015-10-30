package org;

public class EncryptDataException extends DCAUtilsException {

	private static final long serialVersionUID = 1L;
	
	public EncryptDataException() {
		this.errorCode = -10000;
		this.msg = "Encrypt data exception";
	}
}
