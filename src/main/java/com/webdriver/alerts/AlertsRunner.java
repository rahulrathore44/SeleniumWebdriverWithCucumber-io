package com.webdriver.alerts;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/alerts/Alerts.feature"},
		glue = {"com.webdriver.alerts"},
		dryRun = false,
		monochrome = true
		)
public class AlertsRunner extends AbstractTestNGCucumberTests {

}
