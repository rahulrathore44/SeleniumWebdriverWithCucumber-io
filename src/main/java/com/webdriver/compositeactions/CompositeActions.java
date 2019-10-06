package com.webdriver.compositeactions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/compositeactions/CompositeActions.feature"},
		glue = {"com.webdriver.compositeactions","com.webdriver.generichook"},
		dryRun = false,
		monochrome = true
		)
public class CompositeActions extends AbstractTestNGCucumberTests {

}
