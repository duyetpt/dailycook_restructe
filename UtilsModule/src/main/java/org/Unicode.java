package org;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Unicode {
	
	private static Logger	logger	= LoggerFactory.getLogger(Unicode.class);
	
	public static String toAscii(String value) {
		String str = Normalizer.normalize(value, Normalizer.Form.NFKD);
		String regex = "[\\p{InCombiningDiacriticalMarks}}]+";
		
		String result = null;
		try {
			result = new String(str.replaceAll(regex, "").getBytes("ascii"), "ascii");
		} catch (UnsupportedEncodingException e) {
			logger.error("can't convert String: " + value, e);
		}
		
		return result;
	}
}
