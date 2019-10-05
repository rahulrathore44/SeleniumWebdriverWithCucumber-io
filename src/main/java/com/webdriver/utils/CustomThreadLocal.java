package com.webdriver.utils;

public class CustomThreadLocal {
	
	private static ThreadLocal<String> browserType = new ThreadLocal<>();
	
	public static void setValue(String Type) {
		browserType.set(Type);
	}
	
	public static String getValue() {
		return browserType.get();
	}
	
}
