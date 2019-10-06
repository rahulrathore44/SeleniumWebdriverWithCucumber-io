package com.webdriver.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/datadriven/DataDriven.feature"},
		glue = {"com.webdriver.datadriven","com.webdriver.generichook"},
		dryRun = false,
		monochrome = true,
				plugin = {"pretty","json:target/datadrivenrunner.json"}
		)
public class DataDrivenRunner extends AbstractTestNGCucumberTests {

}
