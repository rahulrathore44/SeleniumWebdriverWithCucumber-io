package com.webdriver.io.customrunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cucumber.deps.difflib.DiffRow.Tag;
import gherkin.pickles.PickleTag;
import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

/**
* rathr1
* 
**/
public class CustomAbstractTestNGCucumberTestsWithTag {
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
    	String tagValue = System.getProperty("TagName");
    	
    	if(tagValue == null || tagValue.isEmpty()){
    		return getFeatureList(data);
    	}
    	
    	List<String> tagList = Arrays.asList(tagValue.split(","));
		//List<String> featureList = Arrays.asList("Web Element Functions","Working with Java Script Popup");
		ArrayList<Object[]> modifiedList = new ArrayList<>();
		
		if(data != null){
			for (int i = 0; i < data.length; i++) {
				PickleEventWrapper pickleEventWrapper = (PickleEventWrapper)data[i][0];
				if(!pickleEventWrapper.getPickleEvent().pickle.getTags().isEmpty()){
					for (String aTag : tagList) {
						if(isTagPresent(aTag,pickleEventWrapper.getPickleEvent().pickle.getTags())){
							modifiedList.add(data[i]);
						}
					}
				}
				
			}
		}
		return modifiedList;
	}
    
    private boolean isTagPresent(String aTag,List<PickleTag> aTagList) {
    	
    	return aTagList.stream().filter(new Predicate<PickleTag>() {
			@Override
			public boolean test(PickleTag t) {
				return aTag.equalsIgnoreCase(t.getName());
			}
		}).collect(Collectors.toList()).isEmpty();
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
