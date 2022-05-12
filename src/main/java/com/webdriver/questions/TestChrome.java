package com.webdriver.questions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestChrome {

	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.setBinary("");
		WebDriver driver = new ChromeDriver(options);
	}
}
