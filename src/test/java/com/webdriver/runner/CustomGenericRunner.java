package com.webdriver.runner;


import com.webdriver.io.customrunner.CustomAbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;




@CucumberOptions(
		features = {"src/main/java/com/webdriver"},
		glue = {"com.webdriver"},
		dryRun = false,
		monochrome = true,
				plugin = {"pretty","json:target/customgenericrunner.json"}
		)
public class CustomGenericRunner extends CustomAbstractTestNGCucumberTests {

}

/**
 * 1. To create a generic method that will navigate to corr page
 * 2. Use Factory design patter to get the instance of corr page after the navigation
 * */
 