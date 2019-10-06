package com.webdriver.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/wait/WebdriverWaitFunction.feature"},
		glue = {"com.webdriver.wait"},
		dryRun = false,
		monochrome = true,
		plugin = {"pretty","json:target/webdriverwaitfunctionrunner.json"}
		)
public class WebdriverWaitFunctionRunner extends AbstractTestNGCucumberTests {

}
