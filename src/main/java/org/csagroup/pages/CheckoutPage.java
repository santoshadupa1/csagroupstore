package org.csagroup.pages;

import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends WebActions implements CSALocators{
	
	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	By checkoutbtn = By.xpath(CheckoutBtn);
    By checkoutContinueBtn = By.xpath(Checkout_Continue);
    By continueBtn = By.xpath(ContinueBtn);
    By shippingcost = By.xpath(ShippingCost);
    
    public void clickOnCheckoutBtn()
    {
    	scrollIntoView(checkoutbtn);
    	click(checkoutbtn);
    	System.out.println("Click on checkout button");
    }
    
    public void clickOnContinueBtn()
    {
    	scrollIntoView(continueBtn);
    	click(continueBtn);
    	System.out.println("Click on continue button");
    }
    
    public void clickOnCheckoutContinueBtn()
    {
    	scrollIntoView(checkoutContinueBtn);
    	javaScriptClick(checkoutContinueBtn);
    	System.out.println("Click on continue button");
    }

    public void verifyShippingCostIsDisplayed()
    {
    	waitForElementToAppear(shippingcost);
    	boolean isDisplayed = driver.findElement(shippingcost).isDisplayed();
    	System.out.println("Shipping cost is displayed: " +isDisplayed);
    	String shippingCostValue = driver.findElement(shippingcost).getText();
    	System.out.println("Shipping cost value: " +shippingCostValue);
    }
}
