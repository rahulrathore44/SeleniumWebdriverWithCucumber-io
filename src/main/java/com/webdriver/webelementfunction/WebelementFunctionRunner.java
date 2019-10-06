package com.webdriver.webelementfunction;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/webelementfunction/WebelementFunction.feature"},
		glue = {"com.webdriver.webelementfunction","com.webdriver.generichook"},
		dryRun = false,
		monochrome = true
		)
public class WebelementFunctionRunner extends AbstractTestNGCucumberTests {

}
