package org;

public class DCAUtilsException extends Exception {

	private static final long serialVersionUID = 6843047895388091099L;
	protected int errorCode;
	protected String msg;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
