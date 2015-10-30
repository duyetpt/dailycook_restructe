package com.vn.dailycookapp.security;

import java.util.ArrayList;
import java.util.List;

class NotAuthUrls {
	
	private static final List<String>	list;
	// TODO
	private static final List<String> regexUrl;
	
	static {
		list = new ArrayList<String>();
		regexUrl = new ArrayList<String>();
		// regex
		regexUrl.add("dailycook[/]user[/]{1}[a-zA-Z1-9]+[/]search{1}");
		regexUrl.add("dailycook[/]recipe[/]{1}[a-zA-Z1-9]+[/]comment[/]get{1}");
		//dailycook[/]recipe[/]{1}[a-zA-Z1-9]+[/]comment[/]get{1}
		
		//user
		list.add("dailycook/user/login");
		list.add("dailycook/user/newfeed");
		list.add("dailycook/user/register");
		
		// recipe
		list.add("dailycook/recipe/get");
		list.add("dailycook/recipe/search");
		
		list.add("dailycook/ping");
		list.add("dailycook/hello");
		list.add("dailycook/policy");
	}
	
	/**
	 * 
	 * @param url
	 * @return true if url contain in notAuthUrls list otherwise return true
	 */
	public static boolean isNotAuth(String url) {

		for (String regex : regexUrl) {
			if (url.matches(regex)) return true;
		}
		
		return list.contains(url);
	}
	
	// public static void main(String[] args) {
	// System.out.println(isNotAuth("dailycook/user/xxxxxx/search"));
	// System.out.println(isNotAuth("dailycook/user/xxfa1321/search"));
	// System.out.println(isNotAuth("dailycook/recipe/xxxxxx/comment/get"));
	//
	// System.out.println(isNotAuth("dailycook/recipe/comment/get"));
	// }
}
