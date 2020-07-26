package com.webdriver.generichook;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.webdriver.services.DriverServices;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

@SuppressWarnings(value = "deprecation")
public class GeneralHook {
	
	private DriverServices services;
	private WebDriver driver;
	
	private static CustomExtentBDDReporter customExtentReporter;
	private static boolean isReporterRunning;
	
	public GeneralHook(DriverServices services) {
		this.services = services;
		this.driver = services.getDriver();
	}
	
	@Before(value="@regression",order = 2)
	public void setupForRegression(){
		System.out.println("This is regression suite");
	}
	
	@Before(value="@smoke")
	public void setupForSmoke(){
		System.out.println("This is smoke suite");
	}
	
	@Before(order = 1)
	public void setup() {
		System.out.println(" This is normal hook");
	}
	
	@Before
	public void beforeScenario(Scenario scenario) {
		if(!isReporterRunning){
			customExtentReporter = new CustomExtentBDDReporter("C:\\Data\\log\\TestReport.html");
			isReporterRunning = true;
		}
	}
	
	@After
	public void afterScenario(Scenario scenario) throws IOException, ClassNotFoundException {
		String screenShotFileName = "C:\\Data\\log\\" + scenario.getName().replaceAll(" ", "") + ".jpeg";
		if(scenario.isFailed()){
			services.getGenericHelper().takeScreenShot(screenShotFileName);
		}
		customExtentReporter.createTest(scenario, screenShotFileName);
		customExtentReporter.writeToReport();
		if(driver != null){
	    	driver.quit(); // it will close all the window and stop the web driver
	    }
	}
	
	//@After
	public void teardown(Scenario scenario) {
		if(scenario.isFailed()){
			captureScreenShot(scenario);
		}
		if(driver != null){
	    	driver.quit(); // it will close all the window and stop the web driver
	    }
		
	}
	
	private void captureScreenShot(Scenario scenario) {
		int random = (int)(Math.random() * 1000);
		services.getGenericHelper().takeScrenShot("Screenshot", "src" + random +".png");
		scenario.embed(services.getGenericHelper().takeScrenShot(), "image/png");
	}

}
