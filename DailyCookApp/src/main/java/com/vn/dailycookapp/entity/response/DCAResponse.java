package com.vn.dailycookapp.entity.response;

import org.json.JsonProperty;

public class DCAResponse {
	
	@JsonProperty("error_no")
	private int	error;
	
	@JsonProperty("data")
	private Object	data;
	
	public DCAResponse() {
	}
	
	public DCAResponse(int error) {
		this.error = error;
	}
	
	public int getError() {
		return error;
	}
	
	public void setError(int error) {
		this.error = error;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}
