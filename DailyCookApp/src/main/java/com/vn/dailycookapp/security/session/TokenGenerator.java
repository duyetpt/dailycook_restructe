package com.vn.dailycookapp.security.session;

import java.math.BigInteger;
import java.security.SecureRandom;

class TokenGenerator {
	
	private static final int MAX_BIT = 130;
	private static final SecureRandom randome = new SecureRandom();
	
	static String getToken() {
		BigInteger bigInt = new BigInteger(MAX_BIT, randome);
		
		return bigInt.toString(32);
	}
}
