package com.webdriver.questions;

import java.io.File;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestExtentReport {
	private static ExtentSparkReporter extentHtmlReporter;
	private static ExtentReports extentReports;

	public static void main(String[] args) {
		 
		extentHtmlReporter = new ExtentSparkReporter(new File("C:\\Data\\log\\TestReport - Copy.html"));
		extentHtmlReporter.config().enableTimeline(true);
		extentReports = new ExtentReports();
		extentReports.setAnalysisStrategy(AnalysisStrategy.SUITE);
		extentReports.attachReporter(extentHtmlReporter);

		for (int i = 1; i < 10; i++) {
			extentReports.createTest("Test" + getRandom()).pass("Pass");
		}
		for (int j = 1; j < 10; j++) {
			extentReports.createTest("Test" + getRandom()).fail("fail");
		}

		if(extentReports != null)
			extentReports.flush();

	}

	private static int getRandom() {
		return (int) (Math.random() * 1000);
	}
}
