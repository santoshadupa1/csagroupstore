package org.csagroup.pages;

import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends WebActions implements CSALocators {
	
	private WebDriver driver;

	public RegistrationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	By createAccountBtn = By.xpath(CreateAccount);
	By firstname = By.xpath(Firstname);
	By lastname = By.xpath(Lastname);
	By countrydropdown = By.xpath(CountryDropdown);
	By statedropdown = By.xpath(StateDropdown);
	By industrydropdown = By.xpath(IndustryDropdown);
	By emailid = By.xpath(Email);
	By newpassword = By.xpath(NewPassword);
	By confirmpassword = By.xpath(ConfirmPassword);
	By termscheckbox = By.xpath(TermsCheckbox);
	By privacycheckbox = By.xpath(PrivacyCheckbox);
	By csagroupcheckbox = By.xpath(CSAGroupCheckbox);
	By submitButton = By.xpath(CreateAccountSubmitBtn);
	
	
	public void clickOnCreateAccountBtn()
	{
		scrollIntoView(createAccountBtn);
		click(createAccountBtn);
		System.out.println("Click on Create Account Button");
	}
	
	public void enterFirstName(String fname)
	{
		writeText(firstname, fname);
		System.out.println("Enter First Name: " +fname);		
	}
	
	public void enterLastName(String lname)
	{
		writeText(lastname,  lname);
		System.out.println("Enter Last Name: " +lname);
	}
	
	public void selectProvinceOrState(String state)
	{
		waitForElementToAppear(statedropdown);
		selectByVisibleText(statedropdown, state);
		System.out.println("Select Country: " +state);
	}
	
	public void selectCountry(String country)
	{
		selectByVisibleText(countrydropdown, country);
		System.out.println("Select Country: " +country);
	}
	
	public void selectIndustry(String industry)
	{
		selectByVisibleText(industrydropdown, industry);
		System.out.println("Select Industry: " +industry);
	}

	public void enterEmailId(String email)
	{
		writeText(emailid, email);
		System.out.println("Enter Email Id: " +email);
	}
	
	public void enterNewPassword(String password)
	{
		writeText(newpassword, password);
		System.out.println("Enter New Password: " +password);
	}
	
	public void enterConfirmPassword(String password)
	{
		writeText(confirmpassword, password);
		System.out.println("Enter Confirm Password: " +password);
	}
	
	public void clickOnTermsCheckbox()
	{
		scrollIntoView(termscheckbox);
		click(termscheckbox);
		System.out.println("Click on Terms and Conditions Checkbox");
	}
	public void clickOnPrivacyCheckbox()
	{
		click(privacycheckbox);
		System.out.println("Click on Privacy Checkbox");
	}
	
	public void clickOnCSAGroupCheckbox()
	{
		click(csagroupcheckbox);
		System.out.println("Click on CSA Group Checkbox");
	}
	
	public void clickOnSubmitButton()
	{
		click(submitButton);
		System.out.println("Click on Submit Button");
	}
	
	public String generateDynamicValue(String prefix) {
	    String timestamp = new java.text.SimpleDateFormat("MMddHHmm").format(new java.util.Date());
	    return prefix + timestamp;
	}
	
	public boolean verifyUserCreated(String expectedName) {
	    By headerName = By.xpath("(//*[@class='csa-nav-link csa-nav-menu login_nav']/span[text()='"+expectedName+"'])[2]");
	    waitForElementToAppear(headerName);
	    System.out.println("verifyUserCreated: Checking for user name: " +expectedName);
	    return isElementPresent(headerName);
	}
	
	public String verifyCreatedUser(String username)
	{
		By createuserName = By.xpath("(//span[contains(text(),'"+username+"')])[2]");
		username = driver.findElement(createuserName).getText().trim();
		System.out.println("Created User as :" +username);
		return username;
	}
}
