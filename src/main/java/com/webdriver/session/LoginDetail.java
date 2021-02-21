package com.webdriver.session;

import com.google.gson.Gson;

public class LoginDetail {
	
	private String username;
	private String password;

	public LoginDetail withUserName(String username) {
		this.username = username;
		return this;
	}
	
	public LoginDetail withPassword(String password) {
		this.password = password;
		return this;
	}
	
	public String build() {
		Gson gson = new Gson();
		String body = gson.toJson(this);
		return body;
	}
}
