package com.webdriver.questions.staleelement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebElementDecorator implements WebElement {

	private WebElement element;
	private WebDriver driver;
	private By locator;
	private int timeOutInSeconds = 3;
	private Logger log = LoggerFactory.getLogger(WebElementDecorator.class.getSimpleName());

	public WebElementDecorator(WebElement element, WebDriver driver, By locator) {
		this.element = element;
		this.driver = driver;
		this.locator = locator;
	}

	public WebElementDecorator(WebElement element, WebDriver driver, By locator, int timeOutInSeconds) {
		this.element = element;
		this.driver = driver;
		this.locator = locator;
		this.timeOutInSeconds = timeOutInSeconds;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {

		return retry(() -> {
			return element.getScreenshotAs(target);
		});

	}

	@Override
	public void click() {
		retry(() -> {
			element.click();
			return null;
		});
	}

	@Override
	public void submit() {
		retry(() -> {
			element.submit();
			return null;
		});
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		retry(() -> {
			element.sendKeys(keysToSend);
			return null;
		});
	}

	@Override
	public void clear() {
		retry(() -> {
			element.clear();
			return null;
		});
	}

	@Override
	public String getTagName() {
		Executor<String> tag = new Executor<String>() {

			@Override
			public String execute() {
				return element.getTagName();
			}
		};
		return retry(tag);
	}

	@Override
	public String getAttribute(String name) {
		return retry(() -> {
			return element.getAttribute(name);
		});
	}

	@Override
	public boolean isSelected() {
		return retry(() -> {
			return element.isSelected();
		});
	}

	@Override
	public boolean isEnabled() {
		return retry(() -> {
			return element.isEnabled();
		});
	}

	@Override
	public String getText() {
		return retry(() -> {
			return element.getText();
		});
	}

	@Override
	public List<WebElement> findElements(By by) {
		return retry(() -> {
			return element.findElements(by);
		});
	}

	@Override
	public WebElement findElement(By by) {
		return retry(() -> {
			return element.findElement(by);
		});
	}

	@Override
	public boolean isDisplayed() {
		return retry(() -> {
			return element.isDisplayed();
		});
	}

	@Override
	public Point getLocation() {
		return retry(() -> {
			return element.getLocation();
		});
	}

	@Override
	public Dimension getSize() {
		return retry(() -> {
			return element.getSize();
		});
	}

	@Override
	public Rectangle getRect() {
		return retry(() -> {
			return element.getRect();
		});
	}

	@Override
	public String getCssValue(String propertyName) {
		return retry(() -> {
			return element.getCssValue(propertyName);
		});
	}

	// retry()
	private <T> T retry(Executor<T> executor) {
		try {
			return executor.execute();
		} catch (StaleElementReferenceException e) {
			log.warn(String.format("StaleElementReferenceException caught for %s retrying now...", locator));
		}
		element = waitForElement();
		return executor.execute();
	}

	// waitForElement()
	private WebElement waitForElement() {
		// create the webdriver wait
		// poll for the element
		// ignore the exception like NoSuchElementException
		// once the element is located in the DOM return the element

		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}
}

interface Executor<T> {
	T execute();
}
