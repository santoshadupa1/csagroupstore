package org.csagroup.tests;

import org.csagroup.pages.LoginPage;
import org.csgroup.drivers.DriverManager;
import org.testng.annotations.Test;

public class LoginTest extends DriverManager {
	
	@Test
	public void verifyLoginUser()
	{
		lp = new LoginPage(driver);
		lp.waitForPageLoaded();
		lp.clickOnLogin();		
	    lp.enterLoginCredentials(prop.getProperty("username"), prop.getProperty("password"));
	    lp.waitForPageToLoad();
	    lp.verifyloginUser();
	}

}
