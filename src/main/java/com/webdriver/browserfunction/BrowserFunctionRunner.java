package com.webdriver.browserfunction;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/browserfunction/BrowserFunction.feature"},
		glue = {"com.webdriver.browserfunction"},
		dryRun = false,
		monochrome = true,
		strict = true
		)
public class BrowserFunctionRunner extends AbstractTestNGCucumberTests {

}
