package com.webdriver.questions;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCaptchaHandling {

	@Test
	public void testCaptcha() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addExtensions(Arrays.asList(new File("C:\\Data\\log\\Buster -Captcha-Solver-for-Humans_v1.2.2.crx"),
				new File("C:\\Data\\log\\I-m-not-robot-captcha-clicker_v1.2.1.crx")));
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://recaptcha-demo.appspot.com/recaptcha-v2-checkbox.php");
		TimeUnit.SECONDS.sleep(3);
		driver.quit();
	}

}
