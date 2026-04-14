package org.csagroup.tests;

import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.pages.CSAOnDemandPage;
import org.csagroup.utilities.PropertyReader;
import org.csgroup.drivers.DriverManager;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

public class OnDemandTest extends DriverManager {
	
	CSAOnDemandPage onDemandPage;
	PropertyReader prop = new PropertyReader();
	
	@Test
	@Story("Verify CSA OnDemandPage")
	@Description("Validates OnDemand Page functionality for stage and prod environments")
	public void verifyCSAOnDemandPage()
	{
		lp.clickOnLogin();	
		AllureCaptureScreenshot.step("Click on Login/Register Button");
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
	   AllureCaptureScreenshot.step("Click on OnDemand Link");
	   onDemandPage.initializeTabStack(); 
	   // Tab 1 → Tab 2
	   onDemandPage.switchToNewWindowTab();
	   AllureCaptureScreenshot.step("Switch To New Window Tab");
	   onDemandPage.logoIsDisplayed();
	   AllureCaptureScreenshot.step("Logo is Displayed");
	   onDemandPage.cartIconIsDisplayed();
	   AllureCaptureScreenshot.step("Cart Icon is Displayed");
	   onDemandPage.docuemntLibraryIsDisplayed();
	   AllureCaptureScreenshot.step("Document Library Link is Displayed");
	   onDemandPage.clickOnDocumentLibraryLink();
	   AllureCaptureScreenshot.step("Click on the Document Library Link");
	   onDemandPage.documentLibrarySearchBarIsDisplayed();
	   AllureCaptureScreenshot.step("Document Library Search bar is displayed");
	   onDemandPage.searchDocumentLibrary("Z620.2:20");
	   AllureCaptureScreenshot.step("Search Document as Z620.2:20");
	   onDemandPage.selectDocumentfromSearchResults("CSA Z620.2:20");
	   AllureCaptureScreenshot.step("Search Results Document as CSA Z620.2:20");
	   onDemandPage.favoriteHeartIconIsDisplayed();
	   AllureCaptureScreenshot.step("Favoriate Heart Icon is Displayed");
	   onDemandPage.downloadPDFIsDisplayed();
	   AllureCaptureScreenshot.step("Download PDF is Displayed");
	   onDemandPage.viewOnlineIsDisplayed();
	   AllureCaptureScreenshot.step("View Online is Displayed");
	   onDemandPage.clickOnViewOnlineBtn();
	   AllureCaptureScreenshot.step("Click On View Online Button");
	   // Tab 2 → Tab 3
	   onDemandPage.switchToNewWindowTab();
	   onDemandPage.verifyViewOnlineNavigation();
	   AllureCaptureScreenshot.step("Verify View Online Navigation URL");
	   // Cleanup
	   onDemandPage.closeCurrentTabAndSwitchBack(); // Tab 3 → Tab 2
	   onDemandPage.closeCurrentTabAndSwitchBack(); // Tab 2 → Tab 1
	   AllureCaptureScreenshot.step("Close the New Tabs and back to Parent window");
	   	
	}
}
