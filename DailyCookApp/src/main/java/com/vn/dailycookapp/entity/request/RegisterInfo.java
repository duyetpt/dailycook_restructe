package com.vn.dailycookapp.entity.request;

public class RegisterInfo {
	
	private String	email;
	private String	password;
	private String	re_password;
	private String	language;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getRe_password() {
		return re_password;
	}
	
	public void setRe_password(String re_password) {
		this.re_password = re_password;
	}
	
}
