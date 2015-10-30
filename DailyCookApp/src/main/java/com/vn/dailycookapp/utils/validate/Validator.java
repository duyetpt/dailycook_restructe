package com.vn.dailycookapp.utils.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	private final String	EMAIL_PATTEN	= "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern			pattern;
	private Matcher			matcher;
	
	private Validator() {
		pattern = Pattern.compile(EMAIL_PATTEN);
	}
	
	private static Validator	validator;
	
	public static Validator getInstance() {
		if (validator == null) {
			validator = new Validator();
		}
		
		return validator;
	}
	
	/**
	 * @param obj
	 * @return
	 * @throws ValidateException
	 */
	public boolean isNotNull(Object obj) {
		return obj != null;
	}
	
	/**
	 * @param obj
	 * @return
	 */
	public boolean isNull(Object obj) {
		return obj == null;
	}
	
	/**
	 * @param email
	 * @param errorCode
	 * @param msg
	 * @return
	 */
	public void validateEmail(String email) throws InvalidEmailFormatException {
		if (email == null || email.isEmpty()) {
			throw new InvalidEmailFormatException();
		}
		
		matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			throw new InvalidEmailFormatException();
		}
	}
	
	public void validatePassword(String password) throws InvalidPasswordException{
		if (password == null || password.length() < 6) {
			throw new InvalidPasswordException();
		}
	}
}
