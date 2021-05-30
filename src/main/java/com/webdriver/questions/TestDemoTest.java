package com.webdriver.questions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestDemoTest {
	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.google.com");
		driver.findElement(By.name("q")).sendKeys("selenium");
		driver.findElement(By.xpath("//*[@type='submit']")).sendKeys(Keys.ENTER);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
		webDriverWait.until(waitForElement());

		System.out.println(driver.getCurrentUrl());
	}

	private static Function<WebDriver, Boolean> waitForElement(){
		return new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				WebElement element= webDriver.findElement(By.xpath("//*[@type='submit']"));
				if(! element.isDisplayed()){
					System.err.println("Element not present");
					return true;
				}
				System.err.println("Waiting.for element.....");
				return false;
			}
		};
	}
}
