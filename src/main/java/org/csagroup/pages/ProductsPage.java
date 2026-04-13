package org.csagroup.pages;

import java.util.List;

import org.csagroup.actions.CSALocators;
import org.csagroup.actions.WebActions;
import org.csagroup.utilities.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class ProductsPage extends WebActions implements CSALocators {

	private WebDriver driver;
	
	PropertyReader prop = new PropertyReader();
	
	public ProductsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public By getPDFPaperSection(String sku) {
	    return By.xpath("//*[@id='langsection" + sku + "']");
	}
	
	By searchBox = By.xpath(SearchBox);
	By productslist = By.xpath(ProductTitles);
	By filterlist = By.xpath(Filterlist);
	By PdfPaperItems = By.xpath("//*[@class='csa-tab-content-wraper']/div[2]/div/div[1]");
	
	
	public void searchProduct(String productName) {
		
		//click(searchBox);
		writeText(searchBox, productName);
		System.out.println("Searching for product: " +productName);
		driver.findElement(searchBox).sendKeys(Keys.ENTER);
	}
	
	public void verifyAndSelectProduct(String productName) {

	    List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productslist));
	    // 1. Validate results exist
	    Assert.assertTrue(products.size() > 0, "No products found in search results");
	    logger.info("Total products found: " + products.size());
	    System.out.println("Total products found: " + products.size());
	    boolean productFound = false;
	    // 2. Print + Select using classic for loop
	    for (int i = 0; i < products.size(); i++) {
	        String text = products.get(i).getText().trim();
	        logger.info("Product: " + text);
	        if (text.equalsIgnoreCase(productName)) {
	            wait.until(ExpectedConditions.elementToBeClickable(products.get(i))).click();
	            logger.info("Clicked on product: " + text);
	            System.out.println("Clicked on product: " + text);
	            productFound = true;
	            break;
	        }
	    }
	    // 3. Fail if not found
	    Assert.assertTrue(productFound, "Product not found: " + productName);
	}
	
	public void verifyViewAccessAndRegion()
	{
		By viewAccessButton = By.xpath("//*[text()='View Access']");
		isElementPresent(viewAccessButton);
		System.out.println("View Access button is present");
		String expectedText = "View Access for this document is only available for viewers in Canada.";
		By viewAccessText = By.xpath("//*[@class='allowedR']/p");
		String actualText = readText(viewAccessText).replaceAll("\\s+", " ").trim();
		Assert.assertEquals(actualText, expectedText);
		
	}
	
	public void verifyNewEditionText()
	{
		By neweditionText = By.xpath("//*[@class='overline']");
		String actualText = readText(neweditionText);
		Assert.assertTrue(actualText.equalsIgnoreCase("Newest Edition"));
	}
	
	public By getPDFPaperAddToCartBtn(String sku) {
        // Use contains(@id, sku) to handle id formats like 'sku2022461' or 'skuSKU: 2022461'
        By skuelement = By.xpath("//*[contains(@id,'" + sku + "')]/div[5]/div[2]//button[1]");
        scrollIntoView(skuelement);
        return skuelement;
    }

	public void addProductToCart(String expectedPaperno)
	{
		List<WebElement> pdfPaperItems = driver.findElements(PdfPaperItems);	
		for(int i=0; i<pdfPaperItems.size(); i++)
		{
			String text = pdfPaperItems.get(i).getText().substring(i).trim();
			System.out.println("PDF Paper Item: " + text);
			if(text.equalsIgnoreCase(expectedPaperno))
			{
				By AddToCart = By.xpath("//div[@class='product-cart-info']/div[2]/button");
				driver.findElement(AddToCart).click();
				System.out.println("Click on Add to Cart button");
				break;
			}
		}
//		By papernotext = By.xpath("//*[@id='langsection"+expectedPaperno+"']");
//		scrollIntoView(papernotext);
//		String actualUsername = readText(papernotext)
//				.replaceAll("\\s+", " ").trim();
//		Assert.assertTrue(actualUsername.contains(expectedPaperno), "Selected product does not match expected product.");
//		System.out.println("Selected product matches expected product: " + expectedPaperno);
	}
	
//	public void addProductToCartPage(String productName)
//	{
//		List<WebElement> products = driver.findElements(By.xpath("//*[@class='csa-tab-content-wraper']/div[2]/div/div[1]"));
//	    WebElement prod = products.stream().filter(product->
//		product.findElement(By.xpath("/div")).getText().equals(productName)).findFirst().orElse(null);
//		prod.findElement(By.cssSelector(".common-cta.addCart")).click();
//	}
	public void addProductToCartPage(String sku) {

	    List<WebElement> products = driver.findElements(
	            By.xpath("//div[contains(@class,'csa-tab-inner-content')]"));
	    boolean productFound = false;
	    for (int i = 0; i < products.size(); i++) {
	        WebElement product = products.get(i);
	        // Get SKU text inside this product block
	        String skuText = product.findElement(By.cssSelector(".pSku")).getText();
	        // Extract only number (clean)
	        String actualSku = skuText.replaceAll("[^0-9]", "");
	        if (actualSku.equals(sku)) {
	            product.findElement(By.cssSelector("button.addCart")).click();
	            logger.info("Clicked Add to Cart for SKU: " + sku);
	            productFound = true;
	            break;
	        }
	    }
	    Assert.assertTrue(productFound, "Product with SKU not found: " + sku);
	}
	
	public void clickOnAddToCart()
	{
	    By AddToCart = By.xpath("//*[@class='common-cta addCart']");
		click(AddToCart);
		logger.info("Clicked on Add to Cart button for SKU: ");
		System.out.println("Clicked on Add to Cart button");
	}
	
	By subscriptionBtn = By.xpath(SubscirptionButton);
	By plusIconBtn = By.xpath(PlusIcon);
	
	public void clickOnSubscription()
	{
		javaScriptClick(subscriptionBtn);
		System.out.println("Click on Subscription button");
	}
	
	public void clickOnPlusIcon()
	{
		javaScriptClick(plusIconBtn);
		System.out.println("Click on PlusIcon button");
	}
	
}