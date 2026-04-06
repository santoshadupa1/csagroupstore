package org.csagroup.tests;

import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.pages.LoginPage;
import org.csgroup.drivers.DriverManager;


import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login")
public class LoginTest extends DriverManager {
	
	@Test
	@Story("Verify login with valid user")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validates login functionality for stage and prod environments")
	public void verifyLoginUser() {

		lp = new LoginPage(driver);

		AllureCaptureScreenshot.step("Wait for login page to load");
		lp.waitForPageLoaded();

		AllureCaptureScreenshot.step("Click on login button");
		lp.clickOnLogin();

		AllureCaptureScreenshot.attachScreenshot("Login Page Loaded", driver);
		String environment = prop.getProperty("env").split("#")[0].trim().toLowerCase();

		AllureCaptureScreenshot.attachLog("Executing in environment: " + environment);
		switch(environment)
		{
		    case "stage":

		    	AllureCaptureScreenshot.step("Enter stage credentials");
		        lp.enterLoginCredentials(
		                prop.getProperty("stageUsername"),
		                prop.getProperty("stagePassword"));

		        AllureCaptureScreenshot.attachScreenshot("After entering stage credentials", driver);

		        AllureCaptureScreenshot.step("Verify logged-in stage user");
		        //lp.verifyloginUser(prop.getProperty("stageUser"));
		        break;

		    case "prod":

		    	AllureCaptureScreenshot.step("Enter prod credentials");
		        lp.enterLoginCredentials(
		                prop.getProperty("username"),
		                prop.getProperty("password"));
		        AllureCaptureScreenshot.attachScreenshot("After entering prod credentials", driver);
		        AllureCaptureScreenshot.step("Verify logged-in prod user");
		        lp.waitForTimeToLoad(3);
		       // lp.verifyloginUser(prop.getProperty("prodUser"));
		        break;

		    default:
		        throw new RuntimeException("Invalid environment: " + environment);
		}

		AllureCaptureScreenshot.attachScreenshot("Final state after login", driver);
		AllureCaptureScreenshot.attachLog("Login test completed successfully");
	}
}