package com.webdriver.questions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.webdriver.services.DriverServices;

/**
* rathr1
* 
**/
public class TestXpathOfLocator {
	
	@Test
	public void testGetElementWithXpath(){
		
		DriverServices services = new DriverServices();
		services.getDriver().navigate().to("http://localhost:80/");
		WebElement span = services.getDriver().findElement(By.xpath("//*[@id='header']/ul[1]/li[10]/span"));
		WebElement a = services.getDriver().findElement(By.xpath("//*[@id='header']/ul[1]/li[10]/a"));
		WebElement link = services.getDriver().findElement(By.xpath("//*[@id='header']/ul[1]/li[10]"));
		System.out.println(link.getText().replace(a.getText(), "").replace(span.getText(), "").trim());
	}

}
