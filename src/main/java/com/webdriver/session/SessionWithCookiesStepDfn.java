package com.webdriver.session;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.webdriver.services.DriverServices;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Cookies;

public class SessionWithCookiesStepDfn {

	private WebDriver driver;
	private DriverServices services;
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private Future<Cookies> futureTask;
	private Set<Cookie> driverCookies;
	private Cookies apiCookies;

	public SessionWithCookiesStepDfn(DriverServices services) {
		this.services = services;
		this.driver = services.getDriver();
	}

	@Given("Session_I login into the JIRA application with username {string} password {string} and url {string}")
	public void session_i_login_into_the_JIRA_application_with_username_password_and_url(String username,
			String password, String endpointUrl) throws InterruptedException, ExecutionException {
		Callable<Cookies> task = new Callable<Cookies>() {
			//Login into the JIRA application using the REST API call
			@Override
			public Cookies call() throws Exception {
				String requestBody = new LoginDetail().withUserName(username).withPassword(password).build();
				return new ApplicationLogin().withApplicationUrl(endpointUrl).withRequestBody(requestBody)
						.sendRequest();
			}
		};

		futureTask = executorService.submit(task);
	}

	@Given("Session_I open the JIRA application {string} in the browser")
	public void session_i_open_the_JIRA_application_in_the_browser(String appUrl) {
		driver.manage().deleteAllCookies();
		driver.get(appUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driverCookies = driver.manage().getCookies();
	}

	@Then("Session_Extract the cookies from API call and webdriver session")
	public void session_extract_the_cookies_from_API_call_and_webdriver_session()
			throws InterruptedException, ExecutionException {
		while (!futureTask.isDone()) {}
		//Extract the cookies from the response of API call
		apiCookies = futureTask.get();
	}

	@Then("Session_Conver the restassured cookies to the selenium equivalent")
	public void session_conver_the_restassured_cookies_to_the_selenium_equivalent() {
		driver.manage().deleteAllCookies(); // Clean Old cookies
		Iterator<Cookie> driverCookieItr = driverCookies.iterator();
		Iterator<io.restassured.http.Cookie> apiCookieItr = apiCookies.iterator();
		//Convert the cookies into webdriver equivalent cookies
		//Pass the cookies to the webdriver script
		while (driverCookieItr.hasNext() && apiCookieItr.hasNext()) {
			Cookie newCookie = CookieConverter.convertToSeleniumCookie(driverCookieItr.next(), apiCookieItr.next());
			driver.manage().addCookie(newCookie);
		}
	}

	@Then("Session_Pass the cookies to the webdriver script")
	public void session_pass_the_cookies_to_the_webdriver_script() {
	}

	@Then("Session_Do the browser refresh")
	public void session_do_the_browser_refresh() {
		//Refresh the browser , so that the browser will use the new cookies and reuse the session
		driver.navigate().refresh();
		services.getButtonHelper().click(By.id("create_link"));
	}

}
