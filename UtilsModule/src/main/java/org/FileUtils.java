package org;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	
	private final Logger	logger	= LoggerFactory.getLogger(getClass());
	
	public String readFile(String path) {
		if (path == null || path.isEmpty()) {
			logger.error("file not exist");
			return null;
		}
		
		BufferedReader br = null;
		String result = null;
		try {
			StringBuilder sb = new StringBuilder();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
			// br.mark(1);
			// br.reset();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (FileNotFoundException e) {
			logger.error("file not found", e);
			result = null;
		} catch (IOException e) {
			logger.error("IO exception", e);
			result = null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("IO exception", e);
					result = null;
				}
			}
		}
		return result;
	}
	
	public String readFile(InputStream inputStream) {
		BufferedReader br = null;
		String result = null;
		try {
			StringBuilder sb = new StringBuilder();
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
			// br.mark(1);
			// br.reset();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (FileNotFoundException e) {
			logger.error("file not found", e);
			result = null;
		} catch (IOException e) {
			logger.error("IO exception", e);
			result = null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("IO exception", e);
					result = null;
				}
			}
		}
		return result;
	}
	
	public String readFile(File file) {
		BufferedReader br = null;
		String result = null;
		try {
			StringBuilder sb = new StringBuilder();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (FileNotFoundException e) {
			logger.error("file not found", e);
			result = null;
		} catch (IOException e) {
			logger.error("IO exception", e);
			result = null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("IO exception", e);
					result = null;
				}
			}
		}
		result = removeUTF8BOM(result);
		
		return result;
	}
	
	private static final String	UTF8_BOM	= "\u00EF" + "\u00BB" + "\u00BF";
	
	private static String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			// System.out.println(s);
			s = s.substring(UTF8_BOM.length());
		}
		return s;
	}
}
