package com.webdriver.iframes;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/iframes/Iframes.feature"},
		glue = {"com.webdriver.iframes"},
		dryRun = false,
		monochrome = true
		)
public class IframesRunner extends AbstractTestNGCucumberTests {

}
