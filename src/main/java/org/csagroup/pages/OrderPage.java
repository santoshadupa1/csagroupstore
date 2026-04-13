package org.csagroup.pages;

import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage extends WebActions implements CSALocators {
	
	WebDriver driver;

	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	By orderviewshippingcost = By.xpath(OrderView_ShippingCost);
    By orderViewContinueButton = By.xpath(OrderReViewContinueBtn);
    By orderviewhtstax = By.xpath(OrderView_HTSTax);
    By shippingcost = By.xpath(ShippingCost);
    
    By paymentmethodTotal = By.xpath(PaymentMethod_Total);
    By paymentmethodContinueBtn = By.xpath(PaymentMethodContinueBtn);
    
    By paymentmethodEditCart = By.xpath(PaymentMethod_EditCartBtn);
    By leavecheckoutButtton = By.xpath(LeaveCheckoutBtn);
    By clearCartButton = By.xpath(ClearCartBtn);
    By yesClearCartButton = By.xpath(ClearCartConfirmationBtn);
    
    By ordernumber = By.xpath(OrderNumber);
    By ordersuccessmessage = By.xpath(OrderSuccessMessage);
    
    public void clickOnOrderViewContinueBtn()
    {
    	scrollIntoView(orderViewContinueButton);
    	click(orderViewContinueButton);
    	System.out.println("Click on continue button");
    }
    
    
    public void verifyOrderViewShippingCost()
    {
    	waitForElementToAppear(orderviewshippingcost);
    	boolean isDisplayed = driver.findElement(orderviewshippingcost).isDisplayed();
    	System.out.println("OrderView Shipping cost is displayed: " +isDisplayed);
    	String shippingCostValue = driver.findElement(shippingcost).getText();
    	System.out.println("OrderView Shipping cost value: " +shippingCostValue);
    }
    
    public void verifyOrderViewHTSTax()
    {
    	waitForElementToAppear(orderviewhtstax);
    	boolean isDisplayed = driver.findElement(orderviewhtstax).isDisplayed();
    	System.out.println("OrderView HTSTax is displayed: " +isDisplayed);
    	String HTSTaxValue = driver.findElement(orderviewhtstax).getText();
    	System.out.println("OrderView HTSTax value: " +HTSTaxValue);
    }
    
    public void clickOnPaymentMethodContinueBtn()
    {
    	scrollIntoView(paymentmethodContinueBtn);
    	javaScriptClick(paymentmethodContinueBtn);
    	System.out.println("Click on continue button");
    }
    
    public void verifyPaymentMethodTotal()
    {
    	waitForElementToAppear(paymentmethodTotal);
    	boolean isDisplayed = driver.findElement(paymentmethodTotal).isDisplayed();
    	System.out.println("Payment Method Total is displayed: " +isDisplayed);
    	String TotalValue = driver.findElement(paymentmethodTotal).getText();
    	System.out.println("Payment Method Total value: " +TotalValue);
    }
    
    public void clickOnEditCartBtn()
    {
    	scrollIntoView(paymentmethodEditCart);
    	click(paymentmethodEditCart);
    	System.out.println("Click on Edit Cart button");
    }
    
    public void clickOnLeaveCheckoutBtn()
    {
    	scrollIntoView(leavecheckoutButtton);
    	click(leavecheckoutButtton);
    	System.out.println("Click on checkout button");
    }
    
    public void clickOnClearCartBtn()
    {
    	//scrollIntoView(checkoutbtn);
    	click(clearCartButton);
    	System.out.println("Click on checkout button");
    }
    
    public void clickOnClearCartConfirmationBtn()
    {
    	waitForElementToAppear(yesClearCartButton);
    	click(yesClearCartButton);
    	System.out.println("Clear Cart Confirmation - Click on Yes button");
    }

    public void verifyOrderNumber()
    {
    	waitForElementToAppear(ordernumber);
    	boolean orderno = driver.findElement(ordernumber).isDisplayed();
    	System.out.println("Order Number is displayed: " +orderno);
    	String orderid = driver.findElement(ordernumber).getText();
    	System.out.println("Order Number : " +orderid);
    }
    
    public void verifyOrderSuccessMessage()
    {
    	String orderSuccess = driver.findElement(ordersuccessmessage).getText();
    	System.out.println("Order Success Message : " +orderSuccess);
    }
    
    By viewcsaOnDemandBtn = By.xpath(ViewCSAOnDemand);
    
    public void clickOnCSAOnDemand()
    {
    	javaScriptClick(viewcsaOnDemandBtn);
    	System.out.println("Click on the View CSA OnDemand button");
    }
    
    public void clickOnOrderViewContinueBbutton()
    {
    	 By orderviewContinueBtn = By.xpath(OrderViewContinueButton);
    	scrollIntoView(orderviewContinueBtn);
    	javaScriptClick(orderviewContinueBtn);
    	System.out.println("Click on the Continue in Order View Page");
    }

}
