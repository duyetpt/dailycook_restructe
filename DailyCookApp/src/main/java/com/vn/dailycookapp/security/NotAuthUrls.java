package com.vn.dailycookapp.security;

import java.util.ArrayList;
import java.util.List;

class NotAuthUrls {
	
	private static final List<String>	list;
	private static final List<String> regexUrl;
	
	static {
		list = new ArrayList<>();
		regexUrl = new ArrayList<>();
		// regex
		regexUrl.add("dailycook[/]recipe[/](name|ingredients|tags){1}[/]suggest{1}");
		regexUrl.add("dailycook[/]recipe[/]{1}[a-zA-Z0-9]+[/]comment[/]get{1}");
                regexUrl.add("dailycook[/]recipe[/]report[/](en|vi)[/]reasons");
		regexUrl.add("dailycook[/]user[/]{1}[a-zA-Z0-9]+[/]ban");
		regexUrl.add("dailycook[/]user[/]{1}[a-zA-Z0-9]+[/]profile");
		regexUrl.add("dailycook[/]user[/]{1}[a-zA-Z0-9]+[/]recipes");
		//dailycook[/]recipe[/]{1}[a-zA-Z1-9]+[/]comment[/]get{1}
		
		// admin
		list.add("dailycook/admin/putin93/list/session");
		//user
		list.add("dailycook/user/login");
		list.add("dailycook/user/newfeed");
		list.add("dailycook/user/register");
		list.add("dailycook/user/search");
		
		// recipe
		list.add("dailycook/recipe/get");
		list.add("dailycook/recipe/detailpage");
		list.add("dailycook/recipe/search");
		list.add("dailycook/file/upload");
                
		
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
