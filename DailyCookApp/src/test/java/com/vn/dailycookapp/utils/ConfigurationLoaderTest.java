package com.vn.dailycookapp.utils;

import org.junit.Test;
import static org.junit.Assert.*;
public class ConfigurationLoaderTest {
	
	@Test
	public void test() {
		ConfigurationLoader loader = ConfigurationLoader.getInstance();
		
		System.out.println(loader.getDbName());
		assertNotNull(loader.getDbName());
		
		System.out.println(loader.getImageDirectory());
		assertNotNull(loader.getImageDirectory());
	}
}
