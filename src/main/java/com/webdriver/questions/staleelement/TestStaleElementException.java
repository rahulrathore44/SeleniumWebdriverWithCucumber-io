package com.webdriver.questions.staleelement;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestStaleElementException {

	private final String searchTxtBxId = "quicksearch_top";

	@Test
	public void testStaleElement() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		try {
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.get("http://localhost/");
			WebElement search = driver.findElement(By.id(searchTxtBxId));
			search = new WebElementDecorator(search, driver,
					By.id(searchTxtBxId));
			driver.navigate().refresh(); // make the search ref stale
			search.sendKeys("Stale Element Ref Exception");
			driver.navigate().refresh(); // make the search ref stale
			search.clear();
			driver.navigate().refresh(); // make the search ref stale
			search.sendKeys("Stale Element Ref Exception");
			TimeUnit.SECONDS.sleep(3);

		} finally {
			if (driver != null)
				driver.quit();
		}

	}

}
