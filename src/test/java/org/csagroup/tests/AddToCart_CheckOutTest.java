package org.csagroup.tests;

import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.pages.CartPage;
import org.csagroup.pages.LoginPage;
import org.csagroup.pages.ProductsPage;
import org.csagroup.utilities.PropertyReader;
import org.csgroup.drivers.DriverManager;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

public class AddToCart_CheckOutTest extends DriverManager {
	
	ProductsPage productsPage;
	PropertyReader prop = new PropertyReader();
	CartPage cartPage;
	@Test
	@Story("Add To Cart Functionality")
	@Description("Validates Add to Cart functionality for stage and prod environments")
	public void addToCartAndCheckOut() throws InterruptedException
	{
		//Login to the Application
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
	    lp.waitForPageToLoad();
	    //lp.verifyloginUser("Testfeb5p");
	    // Search and Add Product to Cart
		productsPage = new ProductsPage(driver);
		productsPage.waitForPageLoaded();
		productsPage.searchProduct(prop.getProperty("SearchProduct"));
		AllureCaptureScreenshot.step("Search Prodoucts: " +prop.getProperty("SearchProduct"));
		productsPage.verifyAndSelectProduct(prop.getProperty("ProductName"));
		AllureCaptureScreenshot.step("Select Product as: " +prop.getProperty("ProductName"));
		productsPage.verifyNewEditionText();
		AllureCaptureScreenshot.step("Verify the NewEdition Text is Appeared");
		productsPage.verifyViewAccessAndRegion();
		AllureCaptureScreenshot.step("View Access Button and Text are Appeared");
		productsPage.addProductToCartPage(prop.getProperty("ProductPaper"));
		AllureCaptureScreenshot.step("Product is added to Cart :" +prop.getProperty("ProductPaper"));
		productsPage.waitForTimeToLoad(3);
		// Procced to Cart and Checkout
		cartPage = new CartPage(driver);
		cartPage.scrollToTop();
		cartPage.clickOnGoToCart();
		AllureCaptureScreenshot.step("Click on the Cart Button");
		cartPage.verifyItemQuantity();
		AllureCaptureScreenshot.step("Verify the Item Quantity");
		cartPage.clickOnCheckoutBtn();
		AllureCaptureScreenshot.step("Click on Checkout button in Cart Page");
		cartPage.clickOnContinueBtn();
		AllureCaptureScreenshot.step("Click on Continue");
		cartPage.verifyShippingCostIsDisplayed();
		AllureCaptureScreenshot.step("Verify the Shipping Cost and Displayed");
		cartPage.clickOnCheckoutContinueBtn();
		AllureCaptureScreenshot.step("Click on Checkout Continue button");
		cartPage.waitForTimeToLoad(5);
		cartPage.verifyOrderViewShippingCost();
		AllureCaptureScreenshot.step("Verify the OrderView Shipping Cost");
		cartPage.clickOnOrderViewContinueBtn();
		AllureCaptureScreenshot.step("Click on Order View Continue button");
		cartPage.waitForTimeToLoad(5);
		cartPage.verifyOrderViewHTSTax();
		AllureCaptureScreenshot.step("Order View HTS Tax :" + cartPage.readText(cartPage.orderviewhtstax));
		cartPage.verifyPaymentMethodTotal();
		AllureCaptureScreenshot.step("Payment Method Total :" +driver.findElement(cartPage.paymentmethodTotal).getText());
		cartPage.waitForTimeToLoad(5);
		// Post Condition - Clear Cart
		cartPage.clickOnEditCartBtn();
		AllureCaptureScreenshot.step("Click on Edit Cart button");
		cartPage.waitForTimeToLoad(3);
		cartPage.clickOnLeaveCheckoutBtn();
		AllureCaptureScreenshot.step("Click on Leave Checkout button");
		cartPage.waitForTimeToLoad(3);
		cartPage.clickOnClearCartBtn();
		AllureCaptureScreenshot.step("Click on Clear Cart button");
		cartPage.waitForTimeToLoad(3);
		cartPage.clickOnClearCartConfirmationBtn();
		AllureCaptureScreenshot.step("Clear Cart Confirmation");
		cartPage.waitForTimeToLoad(10);
	}

}
