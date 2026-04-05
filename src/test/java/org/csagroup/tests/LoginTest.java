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
		String environment = prop.getProperty("env").split("#")[0].trim().toLowerCase();
	    switch(environment)
	    {
		    case "stage":
		    	lp.enterLoginCredentials(prop.getProperty("stageUsername"), prop.getProperty("stagePassword"));
		    	lp.verifyloginUser(prop.getProperty("stageUser"));
	            break;
	        case "prod":
	        	lp.enterLoginCredentials(prop.getProperty("username"), prop.getProperty("password"));
	        	lp.verifyloginUser(prop.getProperty("prodUser"));
	            break;
	        default:
	            throw new RuntimeException("Invalid environment: " + environment);	         
	    }
	    
	}

}
