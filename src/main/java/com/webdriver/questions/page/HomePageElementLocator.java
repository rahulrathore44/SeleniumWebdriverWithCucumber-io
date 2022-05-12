package com.webdriver.questions.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageElementLocator {

	@FindBy(how = How.ID, using = "enter_bug")
	public WebElement fileABuglink;
	@FindBy(how = How.ID, using = "quicksearch_main")
	public WebElement quickSearchTextBox;

}
