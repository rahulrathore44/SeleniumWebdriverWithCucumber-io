package com.webdriver.session;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/main/java/com/webdriver/session/SessionWithCookies.feature"},
		glue = {"com.webdriver.session","com.webdriver.generichook"},
		dryRun = false,
		monochrome = true
		)
public class SessionWithCookiesRunner extends AbstractTestNGCucumberTests {

}
