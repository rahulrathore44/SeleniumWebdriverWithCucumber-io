package com.webdriver.session;

import java.util.Date;

import org.openqa.selenium.Cookie;

public class CookieConverter {

	private static String name;
	private static String value;
	private static String path;
	private static String domain;
	private static Date expiry;
	private static boolean isSecure;
	private static boolean isHttpOnly;

	public static Cookie convertToSeleniumCookie(Cookie seleniumCookie, io.restassured.http.Cookie apiCookies) {
		if (seleniumCookie.getName() != null)
			name = apiCookies.getName();
		if (seleniumCookie.getValue() != null)
			value = apiCookies.getValue();
		if (seleniumCookie.getPath() != null)
			path = apiCookies.getPath();
		if (seleniumCookie.getDomain() != null)
			domain = apiCookies.getDomain();
		if (seleniumCookie.getExpiry() != null)
			expiry = apiCookies.getExpiryDate();
		isSecure = seleniumCookie.isSecure() && apiCookies.isSecured() ? true : seleniumCookie.isSecure();
		isHttpOnly = seleniumCookie.isHttpOnly() && apiCookies.isHttpOnly() ? true : seleniumCookie.isHttpOnly();

		return new Cookie(name, value, domain, path, expiry, isSecure, isHttpOnly);
	}

}
