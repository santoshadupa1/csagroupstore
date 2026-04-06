package org.csagroup.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.utilities.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.qameta.allure.Step;



public class LoginPage extends WebActions implements CSALocators{
	
	private WebDriver driver;
	public static Logger logger = LogManager.getLogger(LoginPage.class.getName());
	PropertyReader prop = new PropertyReader();
	
	public LoginPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}	
	
	By loginRegister = By.xpath(LoginRegister);
	By createaccount = By.xpath(CreateAccount);
	By username = By.xpath(Username);
	By password = By.xpath(Password);
	By loginbtn = By.xpath(LoginBtn);
	By Logout = By.xpath("(//button[@id='logout'])[2]");
	By HeaderTitle= By.xpath("(//div[@class='searchbar-header-title'])[3]");
	
	By AcceptAllCookies = By.xpath(AcceptAll);
	By securitycode = By.xpath(SecurityCode);
	By securitycodeSubmit = By.xpath(SecurityCodeSubmitBtn);
		
	public WebElement getLogin()
	{
		return driver.findElement(loginRegister);
	}
	public WebElement getCreateAccount()
	{
		return driver.findElement(createaccount);
	}
	public WebElement getUsername()
	{
		return driver.findElement(username);
	}
	public WebElement getPassword()
	{
		return driver.findElement(password);
	}
	public WebElement getLoginBtn()
	{
		return driver.findElement(loginbtn);
	}
    @Step("Enter username and password")
	public void enterLoginCredentials(String uname, String pswd)
	{
		logger.info("click on Login button");
		//scrollIntoView();
		writeText(username, uname);
		AllureCaptureScreenshot.attachLog("Enter the Username: " + uname);
		System.out.println("Enter the username: " +uname);
		writeText(password, pswd);
		AllureCaptureScreenshot.attachLog("Enter the password: " + pswd);
		System.out.println("Enter the password: " +pswd);
		javaScriptClick(loginbtn);
		AllureCaptureScreenshot.attachLog("Click on Login button");
		System.out.println("click login button action is completed");
	}
    @Step("Click on Login/Register button")
	public void clickOnLogin()
	{
		javaScriptClick(loginRegister);	
		AllureCaptureScreenshot.step("Clicked Login/Register");
		AllureCaptureScreenshot.attachLog("Clicked on Login/Register button");
		System.out.println("Click on the Login/Register");
	}
    @Step("Click on Create Account button")
	public void CreateAccount()
	{
		javaScriptClick(createaccount);
		AllureCaptureScreenshot.attachLog("Clicked on the Create Account button");
		System.out.println("Click on the create account button is  clicked");
	}
    @Step("Click on LogOut button")
	public void Logout()
	{
		javaScriptClick(Logout);
		AllureCaptureScreenshot.attachLog("Clicked on Logout button");
		System.out.println("Click on the logout button is  clicked");
	}
    
	public void verifyLoginPage(String Title)
	{
		readText(HeaderTitle);
		Assert.assertEquals(readText(HeaderTitle), Title);
	}
	
	public WebElement getAcceptAllCookies()
	{
		return driver.findElement(AcceptAllCookies);
	}
	
	public int getAcceptAllAlertSize()
	{
		return driver.findElements(AcceptAllCookies).size();
	}
	
	@Step("Handle Accept Cookies popup")
	public void clickAcceptAllCookies()
	{
		if(getAcceptAllAlertSize()>0)
		{
			javaScriptClick(AcceptAllCookies);
			AllureCaptureScreenshot.attachLog("Accept All Cookies Alert is Displayed and Clicked");
			System.out.println("Accept All Cookies Alert is Displayed and Clicked");
		}
	}
	
	@Step("Verify logged-in user: {0}")
	public void verifyloginUser(String expectedUsername)
	{
		//String expectedUsername = "Testfeb5p";
		By usernametext = By.xpath("(//*[@class='csa-nav-link csa-nav-menu login_nav']/span[text()='"+expectedUsername+"'])[2]");
		waitForElementToAppear(usernametext);
		String actualUsername = readText(usernametext).trim();
		Assert.assertTrue(actualUsername.contains(expectedUsername), "Logged in username does not match expected username.");
		AllureCaptureScreenshot.attachLog("Logged in Usernane is verified successfully: " +actualUsername);
		System.out.println("Logged in Usernane is verified successfully: " +actualUsername);
	}
	
	@Step("Verify Security code")
	public void securityCodeVerification(String code)
	{
		writeText(securitycode, code);
		System.out.println("Enter the security code: " +code);
		javaScriptClick(securitycodeSubmit);
		AllureCaptureScreenshot.attachLog("Click on the security code submit button");
		System.out.println("Click on the security code submit button");
	}
	
	@Step("Verify the Security Code Field Is Presenet")
	public void handleSecurityCodeIfPresent(String code) {

	        try {
	            writeText(securitycode, code);
	            AllureCaptureScreenshot.attachLog("Security code entered: " + code);
	            System.out.println("Security code entered: " + code);
	            javaScriptClick(securitycodeSubmit);
	            AllureCaptureScreenshot.attachLog("Security code is Submitted");
	            System.out.println("Security code submitted");
	        } catch (TimeoutException e) {
	        	AllureCaptureScreenshot.attachLog("Security code not displayed → skipping");
	            System.out.println("Security code not displayed → skipping");
	        }
	 }
}
