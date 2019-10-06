package com.webdriver.io.customrunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

/**
* rathr1
* 
**/
public class CustomAbstractTestNGCucumberTests {
	private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
        // the 'featureWrapper' parameter solely exists to display the feature file in a test report
        testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());
    }

    /**
     * Returns two dimensional array of PickleEventWrapper scenarios
     * with their associated CucumberFeatureWrapper feature.
     *
     * @return a two dimensional array of scenarios features.
     */
    @DataProvider
    public Iterator<Object[]> scenarios() {
    	ArrayList<Object[]> modifiedList = new ArrayList<>();
        if (testNGCucumberRunner == null) {
            //return new Object[0][0];
        	return modifiedList.iterator();
        }
        //Object[][] data = testNGCucumberRunner.provideScenarios();
        //PickleEventWrapper pickleEventWrapper = (PickleEventWrapper)data[0][0];
        //CucumberFeatureWrapper cucumberFeatureWrapper = (CucumberFeatureWrapper)data[0][1];
        modifiedList = filterTheFeature(testNGCucumberRunner.provideScenarios());
        //return testNGCucumberRunner.provideScenarios();
        return modifiedList.iterator();
    }

    // data[0][0] ->> PickleEventWrapper
    // data[0][1] ->> CucumberFeatureWrapper
    
    /*
     * private method, that will return the array list - Contains the feature which we want to execute
     * iterate over 2d object array.
     * using CucumberFeatureWrapper check, 
     * if feature is present
     * 	-- add it to the array list
     * else
     *  -- skip
     * */
    
    private ArrayList<Object[]> filterTheFeature(Object[][] data) {
    	String featureValue = System.getProperty("FeatureName");
    	
    	if(featureValue == null || featureValue.isEmpty()){
    		return getFeatureList(data);
    	}
    	
    	List<String> featureList = Arrays.asList(featureValue.split(","));
		//List<String> featureList = Arrays.asList("Web Element Functions","Working with Java Script Popup");
		ArrayList<Object[]> modifiedList = new ArrayList<>();
		
		if(data != null){
			for (int i = 0; i < data.length; i++) {
				CucumberFeatureWrapper cucumberFeatureWrapper = (CucumberFeatureWrapper)data[i][1];
				if(featureList.contains(cucumberFeatureWrapper.toString().trim().replaceAll("\"", ""))){
					modifiedList.add(data[i]);
					 // data[0][0] ->> PickleEventWrapper i = 0;
				    // data[0][1] ->> CucumberFeatureWrapper
					 // data[1][0] ->> PickleEventWrapper i = 1;
				    // data[1][1] ->> CucumberFeatureWrapper
				}
			}
		}
		return modifiedList;
	}
    
    private ArrayList<Object[]> getFeatureList(Object[][] data) {
    	ArrayList<Object[]> modifiedList = new ArrayList<>();
    	if(data != null){
			for (int i = 0; i < data.length; i++) {
					modifiedList.add(data[i]);
			}
		}
    	return modifiedList;
	}
     
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }

}
