package org.csagroup.pages;

import java.sql.DriverManager;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.csagroup.utilities.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



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
	
	public void enterLoginCredentials(String uname, String pswd)
	{
		logger.info("click on Login button");
		//scrollIntoView();
		//logger.info("Scroll the Page");
		writeText(username, uname);
		logger.info("Enter the username:");
		System.out.println("Enter the username: " +uname);
		writeText(password, pswd);
		logger.info("Enter the password:");
		System.out.println("Enter the password: " +pswd);
		javaScriptClick(loginbtn);
		logger.info("click Login button action is Completed");
		System.out.println("click login button action is completed");
	}

	public void clickOnLogin()
	{
		javaScriptClick(loginRegister);	
		logger.info("Click on the Login/Register button is  clicked");
		System.out.println("Click on the Login/Register button is  clicked");
	}
	public void CreateAccount()
	{
		javaScriptClick(createaccount);
		logger.info("Click on the create account button is  clicked");
		System.out.println("Click on the create account button is  clicked");
	}
	public void Logout()
	{
		javaScriptClick(Logout);
		logger.info("Click on the logout button is  clicked");
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
	
	public void clickAcceptAllCookies()
	{
		if(getAcceptAllAlertSize()>0)
		{
			javaScriptClick(AcceptAllCookies);
			logger.info("Accept All Cookies Alert is Displayed and Clicked");
			System.out.println("Accept All Cookies Alert is Displayed and Clicked");
		}
	}
	
	public void verifyloginUser()
	{
		String expectedUsername = "Testfeb5p";
		By usernametext = By.xpath("(//*[@class='csa-nav-link csa-nav-menu login_nav']/span[text()='"+expectedUsername+"'])[2]");
		String actualUsername = readText(usernametext).trim();
		Assert.assertTrue(actualUsername.contains(expectedUsername), "Logged in username does not match expected username.");
		logger.info("Logged in Usernane is verified successfully");
		System.out.println("Logged in Usernane is verified successfully");
	}
	
	public void securityCodeVerification(String code)
	{
		writeText(securitycode, code);
		System.out.println("Enter the security code: " +code);
		javaScriptClick(securitycodeSubmit);
		System.out.println("Click on the security code submit button");
	}
	
	 public void handleSecurityCodeIfPresent(String code) {

	        try {
	            //String code = prop.getProperty("securityCode");
	            writeText(securitycode, code);
	            System.out.println("Security code entered: " + code);
	            javaScriptClick(securitycodeSubmit);
	            System.out.println("Security code submitted");
	        } catch (TimeoutException e) {
	            System.out.println("Security code not displayed → skipping");
	        }
	    }
}
