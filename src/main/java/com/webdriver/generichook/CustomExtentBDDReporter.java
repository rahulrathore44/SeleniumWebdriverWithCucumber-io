package com.webdriver.generichook;

/**
* rathr1
* 
**/

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Result;

public class CustomExtentBDDReporter {

	private ExtentHtmlReporter extentHtmlReporter;
	private ExtentReports extentReports;
	private ExtentTest extentTest;
	private String featureName = "";
	private String currentFeatureName = "";

	public CustomExtentBDDReporter(String reportLocation) {
		extentHtmlReporter = new ExtentHtmlReporter(new File(reportLocation));
		extentHtmlReporter.config().enableTimeline(true);
		extentHtmlReporter.config().setTheme(Theme.DARK);
		extentHtmlReporter.config().setReportName("Cucmber Execution Report");
		extentReports = new ExtentReports();
		extentReports.setAnalysisStrategy(AnalysisStrategy.BDD);
		extentReports.attachReporter(extentHtmlReporter);
	}

	public void createTest(Scenario scenario, String screenShotFile) throws IOException, ClassNotFoundException {
		if (scenario != null) {
			String testName = getScenarioTitle(scenario);
			featureName = getFeatureName(scenario);

			if (!currentFeatureName.equalsIgnoreCase(featureName)) {
				currentFeatureName = featureName;
				extentTest = extentReports.createTest(new GherkinKeyword("Feature"), currentFeatureName, "");
			}

			switch (scenario.getStatus()) {

			case PASSED:
				extentTest.createNode(testName).pass("Passed");
				break;

			case FAILED:
				String errorMsg = getErrorMessage(scenario);
				extentTest.createNode(testName).fail(errorMsg).addScreenCaptureFromPath(screenShotFile, testName);
				break;

			default:
				extentTest.createNode(testName).skip("Skipped");
			}
		}
	}

	private String getFeatureName(Scenario scenario) {
		Pattern pattern = Pattern.compile("[a-zA-z0-9]*\\.feature$");
		Matcher match = pattern.matcher(scenario.getUri().getPath());
		return match.find() ? match.group() : "UnKnown";
	}

	@SuppressWarnings("unchecked")
	private String getErrorMessage(Scenario scenario) {

		List<Result> testResultList = null;
		List<Result> failedStepList = null;

		try {
			Field stepResults = scenario.getClass().getDeclaredField("delegate");
			stepResults.setAccessible(true);
			Field resutlField = ((stepResults.get(scenario)).getClass().getDeclaredField("stepResults"));
			resutlField.setAccessible(true);
			testResultList = (List<Result>) (resutlField.get(stepResults.get(scenario)));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if (testResultList != null && !testResultList.isEmpty()) {
			failedStepList = testResultList.stream().filter((x) -> {
				return x.getError() != null;
			}).collect(Collectors.toList());
		}

		if (failedStepList != null && !failedStepList.isEmpty()) {
			return failedStepList.get(0).getError().getMessage();
		}

		return "";
	}

	public void writeToReport() {
		if (extentReports != null) {
			extentReports.flush();
		}
	}

	private String getScreenShotLocation(String aLocation) {
		return "file:///" + aLocation.replace("\\", "//");
	}

	private String getScenarioTitle(Scenario scenario) {
		return scenario.getName().replaceAll(" ", " ");
	}

}
