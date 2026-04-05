package org.csagroup.tests;

import org.csagroup.pages.CSAOnDemandPage;
import org.csagroup.utilities.PropertyReader;
import org.csgroup.drivers.DriverManager;
import org.testng.annotations.Test;

public class OnDemandTest extends DriverManager {
	
	CSAOnDemandPage onDemandPage;
	PropertyReader prop = new PropertyReader();
	
	@Test
	public void verifyCSAOnDemandPage()
	{
		lp.clickOnLogin();		
	    String environment = prop.getProperty("env").split("#")[0].trim().toLowerCase();
	    switch(environment)
	    {
		    case "stage":
		    	lp.enterLoginCredentials(prop.getProperty("stageUsername"), prop.getProperty("stagePassword"));
	            break;
	        case "prod":
	        	lp.enterLoginCredentials(prop.getProperty("username"), prop.getProperty("password"));
	            break;
	        default:
	            throw new RuntimeException("Invalid environment: " + environment);	         
	    }
	    
	   onDemandPage = new CSAOnDemandPage(driver);
	   onDemandPage.clickOnDemandLink();
	   onDemandPage.initializeTabStack(); 
	   // Tab 1 → Tab 2
	   onDemandPage.switchToNewWindowTab();
	   onDemandPage.logoIsDisplayed();
	   onDemandPage.cartIconIsDisplayed();
	   onDemandPage.docuemntLibraryIsDisplayed();
	   onDemandPage.clickOnDocumentLibraryLink();
	   onDemandPage.documentLibrarySearchBarIsDisplayed();
	   onDemandPage.searchDocumentLibrary("Z620.2:20");
	   onDemandPage.selectDocumentfromSearchResults("CSA Z620.2:20");
	   onDemandPage.favoriteHeartIconIsDisplayed();
	   onDemandPage.downloadPDFIsDisplayed();
	   onDemandPage.viewOnlineIsDisplayed();
	   onDemandPage.clickOnViewOnlineBtn();
	   // Tab 2 → Tab 3
	   onDemandPage.switchToNewWindowTab();
	   onDemandPage.verifyViewOnlineNavigation();
	   // Cleanup
	   onDemandPage.closeCurrentTabAndSwitchBack(); // Tab 3 → Tab 2
	   onDemandPage.closeCurrentTabAndSwitchBack(); // Tab 2 → Tab 1
	   
		
	}
	
	

}
