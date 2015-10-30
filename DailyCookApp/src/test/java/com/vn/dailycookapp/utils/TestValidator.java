package com.vn.dailycookapp.utils;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.vn.dailycookapp.utils.validate.InvalidEmailFormatException;
import com.vn.dailycookapp.utils.validate.Validator;

public class TestValidator {
	
	static Validator validtor;
	
	@BeforeClass
	public static void init() {
		validtor = Validator.getInstance();
	}
	
	@Test
	public void testIsNotNullFunction() {
		assertEquals(validtor.isNotNull(null), false);
		assertEquals(validtor.isNotNull(""), true);
	}
	
	@Test
	public void testIsNullFunction() {
		assertEquals(validtor.isNull(null), true);
		assertEquals(validtor.isNull(""), false);
	}
	
	@Test
	public void testValidateEmail() throws InvalidEmailFormatException {
		validtor.validateEmail("duyept@gmail.com");
	}
	
	@Test(expected = InvalidEmailFormatException.class)
	public void testValidateEmail1() throws InvalidEmailFormatException {
		validtor.validateEmail("duyeptil.com");
	}
	
	@Test(expected = InvalidEmailFormatException.class)
	public void testValidateEmail2() throws InvalidEmailFormatException {
		validtor.validateEmail(null);
	}
}
