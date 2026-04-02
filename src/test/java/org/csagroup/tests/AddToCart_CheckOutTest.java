package org.csagroup.tests;

import org.csagroup.pages.CartPage;
import org.csagroup.pages.LoginPage;
import org.csagroup.pages.ProductsPage;
import org.csagroup.utilities.PropertyReader;
import org.csgroup.drivers.DriverManager;
import org.testng.annotations.Test;

public class AddToCart_CheckOutTest extends DriverManager {
	
	ProductsPage productsPage;
	PropertyReader prop = new PropertyReader();
	CartPage cartPage;
	@Test
	public void addToCartAndCheckOut() throws InterruptedException
	{
		//Login to the Application
	    lp.clickOnLogin();		
	    lp.enterLoginCredentials(prop.getProperty("username"), prop.getProperty("password"));
	    lp.waitForPageToLoad();
	    lp.verifyloginUser();
	    // Search and Add Product to Cart
		productsPage = new ProductsPage(driver);
		productsPage.waitForPageLoaded();
		productsPage.searchProduct(prop.getProperty("SearchProduct"));
		productsPage.verifyAndSelectProduct(prop.getProperty("ProductName"));
		productsPage.verifyNewEditionText();
		productsPage.verifyViewAccessAndRegion();
		productsPage.addProductToCartPage(prop.getProperty("ProductPaper"));
		// Procced to Cart and Checkout
		cartPage = new CartPage(driver);
		cartPage.scrollToTop();
		cartPage.clickOnGoToCart();
		cartPage.verifyItemQuantity();
		cartPage.clickOnCheckoutBtn();
		cartPage.clickOnContinueBtn();
		cartPage.verifyShippingCostIsDisplayed();
		cartPage.clickOnCheckoutContinueBtn();
		cartPage.verifyOrderViewShippingCost();
		cartPage.clickOnOrderViewContinueBtn();
		cartPage.verifyOrderViewHTSTax();
		cartPage.verifyPaymentMethodTotal();
		// Post Condition - Clear Cart
		cartPage.clickOnEditCartBtn();
		cartPage.clickOnLeaveCheckoutBtn();
		cartPage.clickOnClearCartBtn();
		cartPage.clickOnClearCartConfirmationBtn();
		cartPage.waitForTimeToLoad(10);
	}

}
