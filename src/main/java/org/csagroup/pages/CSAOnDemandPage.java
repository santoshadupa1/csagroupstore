package org.csagroup.pages;

import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class CSAOnDemandPage extends WebActions implements CSALocators {
	
	WebDriver driver;

	public CSAOnDemandPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	By ondemandLink = By.xpath(OnDemandLink);
	By csalogo = By.xpath(CSAlogo);
	By carticon = By.xpath(CartIcon);
	By mylibrarySearchBox = By.xpath(LibrarySearchBox);
	By documentryLibrary = By.xpath(DocumentLibraryLink);
	By searchDocumentlibrary = By.xpath(SearchDocumentLibrary);
	
	By documentLibraryItemslist = By.xpath(DocumentLibraryItems);
	By favoriateheartsymbol = By.xpath(FavoriteHeartIcon);
	By downloadPDFlink = By.xpath(DownloadPDFLink);
	By viewonlineBtn = By.xpath(ViewOnlineButton);
	
	
	public void clickOnDemandLink()
	{
		javaScriptClick(ondemandLink);
		System.out.println("Click on CSA OnDemand link");
	}
	
	public void logoIsDisplayed()
	{
		boolean isDisplayed = isDisplayed(csalogo);
		System.out.println("CSA Logo is displayed: " +isDisplayed);
	}
	
	public void cartIconIsDisplayed()
	{
		boolean isDisplayed = isDisplayed(carticon);
		System.out.println("Cart icon is displayed: " +isDisplayed);
	}
	
	public void mylibrarySearchBoxIsDisplayed()
	{
		boolean isDisplayed = isDisplayed(mylibrarySearchBox);
		System.out.println("My Library SearchBox is Displayed: " +isDisplayed);
	}
	
	public void docuemntLibraryIsDisplayed()
	{
		boolean isDisplayed = isDisplayed(documentryLibrary);
		System.out.println("Document Library link is Displayed: " +isDisplayed);
	}
	
	public void clickOnDocumentLibraryLink()
	{
		javaScriptClick(documentryLibrary);
		System.out.println("Click on Document Library link");
	}
	
	public void documentLibrarySearchBarIsDisplayed()
	{
		boolean isDisplayed = isDisplayed(searchDocumentlibrary);
		System.out.println("Search bar is Displayed: " +isDisplayed);
	}
	
	public void searchDocumentLibrary(String documentName)
	{
		WebElement searchBox = driver.findElement(searchDocumentlibrary);
		searchBox.clear();
		searchBox.sendKeys(documentName);
		searchBox.sendKeys(Keys.ENTER);
		waitForTimeToLoad(5);
	}
	
//	public void selectDocumentfromSearchResults(String documentTitle)
//	{
//		List<WebElement> documentList = driver.findElements(documentLibraryItemslist);
//		int documentCount = documentList.size();
//		System.out.println("No of documents displayed in search results: " +documentCount);
//		for(int i=0; i<documentCount; i++)
//		{
//			documentTitle = documentList.get(i).getText().trim();
//			System.out.println("Document Title: " +documentTitle);
//			if(documentTitle.equalsIgnoreCase(documentTitle))
//			{
//				documentList.get(i).click();
//				System.out.println("Click on selected document:" +documentTitle);
//				break;
//			}		
//		}
//	}
	public void selectDocumentfromSearchResults(String documentName)
	{
	    By docLink = By.xpath("//a[text()='" + documentName + "']");
        javaScriptClick(docLink);
	    System.out.println("Clicked on document: " + documentName);
	    waitForTimeToLoad(10);
	}
	

	public void favoriteHeartIconIsDisplayed()
	{
		boolean isDisplayed = isDisplayed(favoriateheartsymbol);
		System.out.println("Favorite heart icon is Displayed: " +isDisplayed);
	}

	public void downloadPDFIsDisplayed()
	{
		boolean isDisplayed = isDisplayed(downloadPDFlink);
		System.out.println("Download PDF link is Displayed: " +isDisplayed);
	}
	
	public void viewOnlineIsDisplayed()
	{
		boolean isDisplayed = isDisplayed(viewonlineBtn);
		System.out.println("View Online Button is Displayed: " +isDisplayed);
	}
	
	public void clickOnViewOnlineBtn()
	{
		javaScriptClick(viewonlineBtn);
		System.out.println("Click on View Online Button");
	}
	
	public void verifyViewOnlineNavigation()
	{	
		
		waitForTimeToLoad(10);
		String actualURL = driver.getCurrentUrl();
	    System.out.println("New Tab URL: " + actualURL);
	   // Assert.assertEquals(actualURL, "https://view.csagroup.org/nuvJYe");
	  //  switchBackToParent(parentWindow); 
	}
}
