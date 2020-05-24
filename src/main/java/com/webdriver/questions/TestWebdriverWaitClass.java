package com.webdriver.questions;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.webdriver.services.DriverServices;

public class TestWebdriverWaitClass {
	private DriverServices services;

	@BeforeMethod
	public void setUp() {
		services = new DriverServices();
	}

	@Test
	public void test_ignoring_of_exception_in_waitclass() {
		Function<WebDriver, Boolean> waitFuction = new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver input) {
				boolean flag = false;

				try {
					flag = input.findElement(By.id("navCreditCard1")).isDisplayed();
				} catch (Throwable e) {
					throw new NoSuchElementException("Intentionally Done");
				}

				return flag;
			}
		};

		services.getDriver().navigate().to("https://www.bankofamerica.com/");
		services.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		services.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(services.getDriver(), 10);
		wait.ignoring(NoSuchElementException.class);
		wait.pollingEvery(100, TimeUnit.MILLISECONDS);
		wait.until(waitFuction);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() throws IOException {
		services.getDriver().quit();
		services.close();
	}

}
