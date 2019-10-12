package com.webdriver.generichook;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.webdriver.services.DriverServices;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;

public class GeneralHook {
	
	private DriverServices services;
	private WebDriver driver;
	
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
	
	//@After
	public void teardown(Scenario scenario) {
		if(scenario.isFailed()){
			captureScreenShot(scenario);
		}
		if(driver != null){
	    	driver.quit(); // it will close all the window and stop the web driver
	    }
		
	}
	
	private static boolean isReporterRunning = false;
	private static ExtentHtmlReporter htmlReporter;
	private static ExtentReports extentReports;
	
	@Before
	public void beforeScenario(Scenario scenario){
		if(!isReporterRunning){
			htmlReporter = new ExtentHtmlReporter(new File("C:\\Data\\log\\result.html"));
			extentReports = new ExtentReports();
			extentReports.attachReporter(htmlReporter);
			isReporterRunning = true;
		}
	}
	
	@After
	public void afterScenario(Scenario scenario) throws IOException{
		extentReportLogger(scenario);
		if(driver != null){
	    	driver.quit(); // it will close all the window and stop the web driver
	    }
	}

	private void extentReportLogger(Scenario scenario) throws IOException {
		String scenarioName = scenario.getName();
		String fileName = "C:\\Data\\log\\" + scenarioName.replaceAll(" ", "") + ".jpeg";
		if(extentReports != null){
			switch (scenario.getStatus()) {
			case FAILED:
				services.getGenericHelper().takeScrenShot("C:\\Data\\log\\",scenarioName.replaceAll(" ", "") + ".jpeg");
				extentReports.createTest(scenarioName).fail("Failed")
				.addScreenCaptureFromPath(fileName);
				break;
			case PASSED:
				extentReports.createTest(scenarioName).pass("Passed");
				break;
			case SKIPPED:
				extentReports.createTest(scenarioName).skip("Skipped");
				break;
			default:
				//skip
			}
			extentReports.flush();
		}
	}

	private void captureScreenShot(Scenario scenario) {
		int random = (int)(Math.random() * 1000);
		services.getGenericHelper().takeScrenShot("Screenshot", "src" + random +".png");
		scenario.embed(services.getGenericHelper().takeScrenShot(), "image/png");
	}

}
