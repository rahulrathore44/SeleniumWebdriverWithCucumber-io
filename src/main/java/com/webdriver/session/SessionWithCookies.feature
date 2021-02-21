Feature: To reuse the session in the selenium webdriver

Scenario: To reuse the session information with the help of cookies
	Given Session_I login into the JIRA application with username "rahul" password "admin@1234#" and url "http://localhost:9191/rest/auth/1/session"
	And Session_I open the JIRA application "http://localhost:9191/secure/Dashboard.jspa" in the browser
	Then Session_Extract the cookies from API call and webdriver session
	And Session_Conver the restassured cookies to the selenium equivalent
	Then Session_Pass the cookies to the webdriver script
	And Session_Do the browser refresh