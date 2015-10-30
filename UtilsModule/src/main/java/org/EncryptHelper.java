package org;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptHelper {
	
	public static String encrypt(String content) throws EncryptDataException {
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] bytes = md.digest(content.getBytes());
			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				// 0xff : 00 00 00 ff (1111 1111)
				hexString.append(Integer.toHexString(0xFF & bytes[i]));
			}
			
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptDataException();
		}
		
	}
	
//	 public static void main(String[] args) throws DCAException {
//	 System.out.println(encrypt("123456789"));
//	 }
	
}
