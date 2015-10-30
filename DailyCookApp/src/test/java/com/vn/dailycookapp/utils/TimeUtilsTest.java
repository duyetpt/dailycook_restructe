package com.vn.dailycookapp.utils;

import java.util.Date;

import org.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

public class TimeUtilsTest {
	
	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		Date date = new Date(TimeUtils.getCurrentGMTTime());
		StringBuilder sb = new StringBuilder();
		sb.append(date.getYear() + 1900).append("_");
		
		int month = date.getMonth() + 1;
		String monthStr = month > 9 ? String.valueOf(month) : "0" + String.valueOf(month); 
		sb.append(monthStr).append("_");
		
		int dateOfMonth = date.getDate();
	    String dateOfMonthStr = dateOfMonth > 9 ? String.valueOf(dateOfMonth) : "0" + String.valueOf(dateOfMonth);
		sb.append(dateOfMonthStr);
		
		String yyyyMMdd = TimeUtils.getDateyyyyMMdd();
		Assert.assertEquals(sb.toString(), yyyyMMdd);
	}
}
