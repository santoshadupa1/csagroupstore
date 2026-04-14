package org.csagroup.tests;

import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.pages.RegistrationPage;
import org.csagroup.utilities.PropertyReader;
import org.csgroup.drivers.DriverManager;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

public class CreateAccountTest extends DriverManager
{
	RegistrationPage registrationPage;
	PropertyReader prop = new PropertyReader();
	
	@Test()
	@Story("Verify Create Account Functionality")
	@Description("Validates Create Account functionality for stage and prod environments")
	public void createAccountTest()
	{
		lp.clickOnLogin();
		registrationPage = new RegistrationPage(driver);
		registrationPage.clickOnCreateAccountBtn();
		AllureCaptureScreenshot.step("Click on Create Account button");
		String dynamicName = registrationPage.generateDynamicValue("test");
	    String email = "csaqatest" + "+" + dynamicName.substring(4) + "@gmail.com";
	    registrationPage.enterFirstName(dynamicName);
	    AllureCaptureScreenshot.step("Enter the First Name :" +dynamicName);
		registrationPage.enterLastName(prop.getProperty("LastName"));
		AllureCaptureScreenshot.step("Enter the Last Name :" +prop.getProperty("LastName"));
		registrationPage.selectCountry(prop.getProperty("Country"));
		AllureCaptureScreenshot.step("Select Country as :" +prop.getProperty("Country"));
		registrationPage.selectProvinceOrState(prop.getProperty("State"));
		AllureCaptureScreenshot.step("Select the State as :" +prop.getProperty("State"));
		registrationPage.selectIndustry(prop.getProperty("Industry"));
		AllureCaptureScreenshot.step("Select the Industry as :" +prop.getProperty("Industry"));
		registrationPage.enterEmailId(email);
		AllureCaptureScreenshot.step("Enter the Email as :" +email);
		registrationPage.enterNewPassword(prop.getProperty("NewPassword"));
		AllureCaptureScreenshot.step("Enter the New Password as :" +prop.getProperty("NewPassword"));
		registrationPage.enterConfirmPassword(prop.getProperty("ConfirmPassword"));
		AllureCaptureScreenshot.step("Enter the ConfirmPassword as :" +prop.getProperty("ConfirmPassword"));
		registrationPage.clickOnTermsCheckbox();
		AllureCaptureScreenshot.step("Click on Terms Checkbox");
		registrationPage.clickOnPrivacyCheckbox();
		AllureCaptureScreenshot.step("Click on Privacy Checkbox");
		registrationPage.clickOnCSAGroupCheckbox();
		AllureCaptureScreenshot.step("Click on CSA Group Checkbox");
		//registrationPage.clickOnSubmitButton();
		//AllureCaptureScreenshot.step("Click on Submit Button");
		//registrationPage.verifyUserCreated(dynamicName);
		//AllureCaptureScreenshot.step("Verify the User Created");
	}

}
