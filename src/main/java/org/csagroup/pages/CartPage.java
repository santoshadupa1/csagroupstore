package org.csagroup.pages;

import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage extends WebActions implements CSALocators {
	
	private WebDriver driver;
	private WebDriverWait wait;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	By cartlink        = By.xpath(CartLink);
	By itemquantity    = By.xpath(ItemQuantity);
	By checkoutbtn     = By.xpath(CheckoutBtn);
	By checkoutContinueBtn  = By.xpath(Checkout_Continue);
	By continueBtn     = By.xpath(ContinueBtn);
	By shippingcost    = By.xpath(ShippingCost);
	By orderviewshippingcost = By.xpath(OrderView_ShippingCost);
	By orderViewContinueButton = By.xpath(OrderReViewContinueBtn);
	public By orderviewhtstax   = By.xpath(OrderView_HTSTax);
	public By paymentmethodTotal = By.xpath(PaymentMethod_Total);
	By paymentmethodContinueBtn  = By.xpath(PaymentMethodContinueBtn);
	By paymentmethodEditCart     = By.xpath(PaymentMethod_EditCartBtn);
	By leavecheckoutButtton      = By.xpath(LeaveCheckoutBtn);
	By clearCartButton           = By.xpath(ClearCartBtn);
	By yesClearCartButton        = By.xpath(ClearCartConfirmationBtn);

	// ─────────────────────────────────────────────
	// Helpers
	// ─────────────────────────────────────────────
	public void scrollToTop() {
		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo({top:0, behavior:'smooth'})");
	}

	// ─────────────────────────────────────────────
	// Cart Navigation
	// ─────────────────────────────────────────────
	public void clickOnGoToCart() {
		// Use presenceOfElementLocated + JS click
		// because [2] (desktop nav) is hidden in headless viewport
		try {
			WebElement cart = wait.until(
				ExpectedConditions.presenceOfElementLocated(cartlink)
			);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", cart);
			System.out.println("Click on Cart link");
		} catch (Exception e) {
			System.out.println("Primary cart locator failed, trying fallback...");
			// Fallback: click any cart link found in DOM
			List<WebElement> cartLinks = driver.findElements(
				By.xpath("//a[contains(@class,'csa-cart-link')]")
			);
			System.out.println("Cart links found on page: " + cartLinks.size());
			boolean clicked = false;
			for (WebElement link : cartLinks) {
				try {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
					System.out.println("Clicked cart via fallback. href=" 
						+ link.getAttribute("href"));
					clicked = true;
					break;
				} catch (Exception ex) {
					// try next
				}
			}
			if (!clicked) {
				throw new RuntimeException("Could not click any cart link on page", e);
			}
		}
	}

	// ─────────────────────────────────────────────
	// Item Quantity
	// ─────────────────────────────────────────────
	public void verifyItemQuantity() {
		WebElement quantityElement = wait.until(
			ExpectedConditions.visibilityOfElementLocated(itemquantity)
		);
		String quantity = quantityElement.getText();
		System.out.println("Item quantity in cart: " + quantity);

		// Extract number and warn early if not what expected
		String numStr = quantity.replaceAll("[^0-9]", "");
		int itemCount = numStr.isEmpty() ? 0 : Integer.parseInt(numStr);
		if (itemCount < 2) {
			System.out.println("WARNING: Expected 2 items in cart but found: " + itemCount
				+ " — shipping section may not render.");
		}
	}

	// ─────────────────────────────────────────────
	// Checkout Buttons
	// ─────────────────────────────────────────────
	public void clickOnCheckoutBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(checkoutbtn));
		scrollIntoView(checkoutbtn);
		javaScriptClick(checkoutbtn);
		System.out.println("Click on checkout button");
	}

	public void clickOnContinueBtn() {
		waitForElementToPresnece(continueBtn);
		scrollIntoView(continueBtn);
		javaScriptClick(continueBtn);
		System.out.println("Click on continue button");
	}

	public void clickOnCheckoutContinueBtn() {
		waitForElementToPresnece(checkoutContinueBtn);
		scrollIntoView(checkoutContinueBtn);
		javaScriptClick(checkoutContinueBtn);
		System.out.println("Click on checkout continue button");
	}

	public void clickOnOrderViewContinueBtn() {
		waitForElementToPresnece(orderViewContinueButton);
		scrollIntoView(orderViewContinueButton);
		javaScriptClick(continueBtn);
		System.out.println("Click on order view continue button");
	}

	// ─────────────────────────────────────────────
	// ✅ PRIMARY FIX: Shipping Cost Verification
	// ─────────────────────────────────────────────
	public void verifyShippingCostIsDisplayed()
    {
    	try {
    		WebElement shippingElement = wait.until(
    			ExpectedConditions.visibilityOfElementLocated(shippingcost));
    		boolean isDisplayed = shippingElement.isDisplayed();
    		System.out.println("Shipping cost is displayed: " + isDisplayed);
    		String shippingCostValue = shippingElement.getText();
    		System.out.println("Shipping cost value: " + shippingCostValue);
    	} catch (Exception e) {
    		System.out.println("Shipping cost element not found within timeout. XPath: " + ShippingCost);
    		throw e;
    	}
    }

	// ─────────────────────────────────────────────
	// Order View
	// ─────────────────────────────────────────────
	public void verifyOrderViewShippingCost() {
		WebElement orderShippingElement = wait.until(
			ExpectedConditions.visibilityOfElementLocated(orderviewshippingcost)
		);
		System.out.println("OrderView Shipping cost is displayed: " 
			+ orderShippingElement.isDisplayed());
		System.out.println("OrderView Shipping cost value: " 
			+ orderShippingElement.getText());
	}

	public void verifyOrderViewHTSTax() {
		WebElement htsTaxElement = wait.until(
			ExpectedConditions.visibilityOfElementLocated(orderviewhtstax)
		);
		System.out.println("OrderView HTSTax is displayed: " + htsTaxElement.isDisplayed());
		System.out.println("OrderView HTSTax value: " + htsTaxElement.getText());
	}

	// ─────────────────────────────────────────────
	// Payment
	// ─────────────────────────────────────────────
	public void clickOnPaymentMethodContinueBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(paymentmethodContinueBtn));
		scrollIntoView(paymentmethodContinueBtn);
		javaScriptClick(paymentmethodContinueBtn);
		System.out.println("Click on payment method continue button");
	}

	public void verifyPaymentMethodTotal() {
		WebElement totalElement = wait.until(
			ExpectedConditions.visibilityOfElementLocated(paymentmethodTotal)
		);
		System.out.println("Payment Method Total is displayed: " + totalElement.isDisplayed());
		System.out.println("Payment Method Total value: " + totalElement.getText());
	}

	// ─────────────────────────────────────────────
	// Post Condition - Clear Cart
	// ─────────────────────────────────────────────
	public void clickOnEditCartBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(paymentmethodEditCart));
		scrollIntoView(paymentmethodEditCart);
		javaScriptClick(paymentmethodEditCart);
		System.out.println("Click on Edit Cart button");
	}

	public void clickOnLeaveCheckoutBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(leavecheckoutButtton));
		scrollIntoView(leavecheckoutButtton);
		javaScriptClick(leavecheckoutButtton);
		System.out.println("Click on Leave checkout button");
	}

	public void clickOnClearCartBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(clearCartButton));
		javaScriptClick(clearCartButton);
		System.out.println("Click on Clear cart button");
	}

	public void clickOnClearCartConfirmationBtn() {
		waitForElementToAppear(yesClearCartButton);
		wait.until(ExpectedConditions.elementToBeClickable(yesClearCartButton));
		javaScriptClick(yesClearCartButton);
		System.out.println("Clear Cart Confirmation - Click on Yes button");
	}

	// ─────────────────────────────────────────────
	// Utility
	// ─────────────────────────────────────────────
	public String readText(By locator) {
		return driver.findElement(locator).getText();
	}
}