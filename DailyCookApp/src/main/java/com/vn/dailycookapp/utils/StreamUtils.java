package com.vn.dailycookapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamUtils {
	
	private final static Logger	logger		= LoggerFactory.getLogger(StreamUtils.class);
	private static final int	BUF_SIZE	= 8 * 1024;
	
	private static byte[] readFromStream(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = new byte[BUF_SIZE];
		int nRead = 0;
		try {
			while ((nRead = inputStream.read(bytes)) != -1) {
				baos.write(bytes, 0, nRead);
			}
			
			baos.flush();
			inputStream.close();
		} catch (IOException e) {
			logger.error("read data form inputStream errorr!", e);
		}
		
		return baos.toByteArray();
	}
	
	public static String saveImage(InputStream inputStream, String imgFormat) {
		byte[] image = readFromStream(inputStream);
		FileOutputStream fos = null;
		String relativePath = getRelativePath(imgFormat);
		
		StringBuilder sb = new StringBuilder();
		sb.append(ConfigurationLoader.getInstance().getImageDirectory()).append("/");
		sb.append(relativePath);
		
		try {
			fos = new FileOutputStream(sb.toString());
			fos.write(image);
			fos.flush();
		} catch (IOException e) {
			logger.error("Write image to disk error!", e);
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				logger.error("close outputstream error!", e);
			}
		}
		
		return relativePath;
	}
	
	private static String getRelativePath(String imgFormat) {
		String folderName = TimeUtils.getDateyyyyMMdd();
		String directory = ConfigurationLoader.getInstance().getImageDirectory() + "/" + folderName;
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		StringBuilder sb = new StringBuilder(folderName);
		sb.append("/").append(generateFileName()).append(".").append(imgFormat);
		return sb.toString();
	}
	
	private static String generateFileName() {
		String uniqueID = UUID.randomUUID().toString();
		
		return uniqueID;
	}
}
