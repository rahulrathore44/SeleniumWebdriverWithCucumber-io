package com.webdriver.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/webelementfunction/WebelementFunction.feature"},
		glue = {"com.webdriver.webelementfunction","com.webdriver.generichook"},
		dryRun = false,
		monochrome = true,
		plugin = {"pretty","json:target/webdriverwaitfunctionrunner.json"}
		)
public class WebelementFunctionRunner extends AbstractTestNGCucumberTests {

}
