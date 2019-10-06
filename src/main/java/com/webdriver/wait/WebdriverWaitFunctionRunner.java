package com.webdriver.wait;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/wait/WebdriverWaitFunction.feature"},
		glue = {"com.webdriver.wait"},
		dryRun = false,
		monochrome = true,
		plugin = {"pretty"}
		)
public class WebdriverWaitFunctionRunner extends AbstractTestNGCucumberTests {

}
