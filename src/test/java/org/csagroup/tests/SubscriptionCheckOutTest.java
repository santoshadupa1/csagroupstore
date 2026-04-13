package org.csagroup.tests;

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
	@Story("Add To Cart for Scription Product")
	public void verifyScriptionCheckout()
	{
		lp.clickOnLogin();		
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
		productsPage.verifyAndSelectProduct(prop.getProperty("ProductName1"));
		productsPage.verifyViewAccessAndRegion();
		productsPage.clickOnSubscription();
		productsPage.clickOnPlusIcon();
		productsPage.waitForTimeToLoad(3);
		productsPage.addProductToCartPage(prop.getProperty("ScriptionNo"));
		productsPage.waitForTimeToLoad(10);
		cartPage = new CartPage(driver);
		cartPage.scrollToTop();
		cartPage.clickOnGoToCart();
		cartPage.clickOnCheckoutBtn();
		cartPage.waitForTimeToLoad(3);
		cartPage.clickOnContinueBtn();
		cartPage.waitForTimeToLoad(5);
		orderPage = new OrderPage(driver);
		orderPage.clickOnOrderViewContinueBbutton();
		orderPage.waitForTimeToLoad(5);
		orderPage.verifyOrderSuccessMessage();
        orderPage.verifyOrderNumber();
        orderPage.clickOnCSAOnDemand();
        orderPage.waitForTimeToLoad(5);
	}

}
