package org.csagroup.pages;

import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends WebActions implements CSALocators {
	
	private WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	By cartlink = By.xpath(CartLink);
	By itemquantity = By.xpath(ItemQuantity);
    By checkoutbtn = By.xpath(CheckoutBtn);
    By checkoutContinueBtn = By.xpath(Checkout_Continue);
    By continueBtn = By.xpath(ContinueBtn);
    By shippingcost = By.xpath(ShippingCost);
    By orderviewshippingcost = By.xpath(OrderView_ShippingCost);
    By orderViewContinueButton = By.xpath(OrderReViewContinueBtn);
    By orderviewhtstax = By.xpath(OrderView_HTSTax);
    By paymentmethodTotal = By.xpath(PaymentMethod_Total);
    By paymentmethodContinueBtn = By.xpath(PaymentMethodContinueBtn);
    
    By paymentmethodEditCart = By.xpath(PaymentMethod_EditCartBtn);
    By leavecheckoutButtton = By.xpath(LeaveCheckoutBtn);
    By clearCartButton = By.xpath(ClearCartBtn);
    By yesClearCartButton = By.xpath(ClearCartConfirmationBtn);
    
    public void scrollToTop() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo({top:0, behavior:'smooth'})");
    }
    
    public void clickOnGoToCart()
    {
    	WebElement cart = driver.findElement(cartlink);   	
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", cart);
    	System.out.println("Click on Cart link");
    }
    
    public void verifyItemQuantity()
    {
    	String quantity = driver.findElement(itemquantity).getText();
    	System.out.println("Item quantity in cart: " +quantity);
    }
    
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
    
    public void clickOnOrderViewContinueBtn()
    {
    	scrollIntoView(orderViewContinueButton);
    	click(continueBtn);
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
}
