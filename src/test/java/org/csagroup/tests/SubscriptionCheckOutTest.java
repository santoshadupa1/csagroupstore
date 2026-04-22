package org.csagroup.tests;

import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.pages.CartPage;
import org.csagroup.pages.OrderPage;
import org.csagroup.pages.ProductsPage;
import org.csagroup.utilities.PropertyReader;
import org.csgroup.drivers.DriverManager;
import org.testng.annotations.Test;

import io.qameta.allure.Story;

public class SubscriptionCheckOutTest extends DriverManager {
	
	ProductsPage productsPage;
	PropertyReader prop = new PropertyReader();
	CartPage cartPage;
	OrderPage orderPage;
	
	@Test
	@Story("Add To Cart for Subscription Product")
	public void verifyScriptionCheckout()
	{
		lp.clickOnLogin();	
		AllureCaptureScreenshot.step("Click on Login/Register Button");
	    switch(getEnv())
	    {
		    case "stage":
		    	lp.enterLoginCredentials(prop.getProperty("stageUsername"), prop.getProperty("stagePassword"));
	            break;
	        case "prod":
	        	lp.enterLoginCredentials(prop.getProperty("username"), prop.getProperty("password"));
	            break;
	        default:
	            throw new RuntimeException("Invalid environment: " + getEnv());	         
	    }
	    
	    productsPage = new ProductsPage(driver);
		productsPage.waitForTimeToLoad(10);
		productsPage.searchProduct(prop.getProperty("SearchProduct1"));
		AllureCaptureScreenshot.step("Search Product as :" +prop.getProperty("SearchProduct1"));
		productsPage.verifyAndSelectProduct(prop.getProperty("ProductName1"));
		AllureCaptureScreenshot.step("verify and Select Product as :" +prop.getProperty("ProductName1"));
		productsPage.verifyViewAccessAndRegion();
		AllureCaptureScreenshot.step("View Access button is present");
		productsPage.clickOnSubscription();
		AllureCaptureScreenshot.step("Click on Subscription Button");
		productsPage.clickOnPlusIcon();
		AllureCaptureScreenshot.step("Click on Plus Icon Button");
		productsPage.waitForTimeToLoad(3);
		productsPage.addProductToCartPage(prop.getProperty("ScriptionNo"));
		AllureCaptureScreenshot.step("Add Product to Cart :" +prop.getProperty("ScriptionNo"));
		productsPage.waitForTimeToLoad(10);
		cartPage = new CartPage(driver);
		cartPage.scrollToTop();
		cartPage.clickOnGoToCart();
		AllureCaptureScreenshot.step("Click on Cart button");
		cartPage.clickOnCheckoutBtn();
		AllureCaptureScreenshot.step("Click on Checkout Button");
		cartPage.waitForTimeToLoad(3);
		cartPage.clickOnContinueBtn();
		AllureCaptureScreenshot.step("Click on Continue Button");
		cartPage.waitForTimeToLoad(5);
		orderPage = new OrderPage(driver);
		orderPage.clickOnOrderViewContinueBbutton();
		AllureCaptureScreenshot.step("Click on Order View Continue Button");
		orderPage.waitForTimeToLoad(5);
		orderPage.verifyOrderSuccessMessage();
		AllureCaptureScreenshot.step("Verify the Order Success Message :" +driver.findElement(orderPage.ordersuccessmessage).getText());
        orderPage.verifyOrderNumber();
        AllureCaptureScreenshot.step("Verify the Order Number :" +driver.findElement(orderPage.ordernumber).getText());      
        orderPage.clickOnCSAOnDemand();
        AllureCaptureScreenshot.step("Click on CSA OnDemand Button");
        orderPage.waitForTimeToLoad(5);
	}

}
