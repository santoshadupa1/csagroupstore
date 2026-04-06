package org.csagroup.tests;

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
		String dynamicName = registrationPage.generateDynamicValue("test");
	    String email = "csaqatest" + "+" + dynamicName.substring(4) + "@gmail.com";
	    registrationPage.enterFirstName(dynamicName);
		registrationPage.enterLastName(prop.getProperty("LastName"));
		registrationPage.selectCountry(prop.getProperty("Country"));
		registrationPage.selectProvinceOrState(prop.getProperty("State"));
		registrationPage.selectIndustry(prop.getProperty("Industry"));
		registrationPage.enterEmailId(email);
		registrationPage.enterNewPassword(prop.getProperty("NewPassword"));
		registrationPage.enterConfirmPassword(prop.getProperty("ConfirmPassword"));
		registrationPage.clickOnTermsCheckbox();
		registrationPage.clickOnPrivacyCheckbox();
		registrationPage.clickOnCSAGroupCheckbox();
		//registrationPage.clickOnSubmitButton();
		//registrationPage.verifyUserCreated(dynamicName);
	}

}
