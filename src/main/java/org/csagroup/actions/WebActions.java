package org.csagroup.actions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csagroup.utilities.PropertyReader;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;


/**
 * BasePage contains common reusable web actions used by page objects.
 * The class favors small, well-named helper methods to make tests easier to read
 * and to provide small fault-tolerant wrappers around Selenium operations.
 */
public class WebActions {

    public static Logger logger = LogManager.getLogger(WebActions.class.getName());
    public WebDriver driver;
    public WebDriverWait wait;
    public Select select;
    public Actions actions;
    public Alert alert;
    private JavascriptExecutor js;
    static final int TIMEOUT = 40;
    static final int POLLING = 100;
    public static String testName;
    public static String title;

    PropertyReader pr = new PropertyReader();
  //  Screenshot screenhsot;

    // Constructor: initialize reusable helpers once per page object
    public WebActions(WebDriver driver) {
        this.driver = driver;
        // keep existing behaviour but use Duration if available for clarity
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            wait.pollingEvery(Duration.ofMillis(POLLING));
        } catch (Throwable t) {
            // If older Selenium is used, fall back to older constructor
        	wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        }
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    // -------------------------------
    // Small helper convenience methods
    // -------------------------------

    /**
     * Waits for visibility and returns the WebElement. Wraps findElement with wait.
     */
    public WebElement safeFindElement(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            WebElement el = driver.findElement(by);
            return el;
        } catch (Throwable t) {
            logger.error("Element not found or not visible: {}", by, t);
            throw t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t);
        }
    }

    /**
     * Safe click with retry and interception-recovery logic.
     */
    public void safeClick(By by) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForElementToClick(by);
                driver.findElement(by).click();
                return;

            } catch (StaleElementReferenceException stale) {
                logger.warn("StaleElementReference caught for {} - retrying", by);
                waitForElementToClick(by);
            } catch (ElementClickInterceptedException eci) {
                logger.warn("ElementClickIntercepted for {}: {}. Attempting recovery (attempt {})", by, eci.getMessage(), attempts + 1);
                recoverFromClickInterception(by);
            } catch (Throwable t) {
                logger.error("Click failed for {}: {}", by, t.getMessage());
                throw t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t);
            }

            attempts++;
            sleepTime(1);
        }
        throw new RuntimeException("Unable to click element after retries: " + by);
    }

    // Recovery strategy when a click is intercepted: try hiding common blockers, scroll & re-click
    private void recoverFromClickInterception(By by) {
        try {
            // Hide some common blocking elements if present (project-specific id/class)
            List<By> blockers = List.of(
                    By.id("hidden-search-box"),
                    By.cssSelector(".overlay"),
                    By.cssSelector(".modal-backdrop"),
                    By.cssSelector(".ui-widget-overlay")
            );
            for (By b : blockers) {
                List<WebElement> els = driver.findElements(b);
                for (WebElement el : els) {
                    try {
                        if (el.isDisplayed()) {
                            logger.info("Hiding blocking element {}", b);
                            js.executeScript("arguments[0].style.display='none';", el);
                            sleepTime(1);
                        }
                    } catch (Throwable ignore) {
                        logger.debug("Could not hide blocker {}: {}", b, ignore.getMessage());
                    }
                }
            }
        } catch (Throwable t) {
            logger.debug("Error while attempting to hide blockers: {}", t.getMessage());
        }

        // Try to bring the target element into view and click using Actions or JS as fallback
        try {
            WebElement el = driver.findElement(by);
            try {
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
            } catch (Throwable ignore) {
                js.executeScript("arguments[0].scrollIntoView(true);", el);
            }
            sleepTime(1);
            try {
                actions.moveToElement(el).click().perform();
            } catch (Throwable a) {
                logger.debug("Actions click failed: {}. Falling back to JS click", a.getMessage());
                try {
                    js.executeScript("arguments[0].click();", el);
                } catch (Throwable jsEx) {
                    logger.debug("JS click also failed: {}", jsEx.getMessage());
                }
            }
        } catch (Throwable t) {
            logger.debug("Recovery attempt failed to find or click element {}: {}", by, t.getMessage());
        }
    }

    // Wait for element to appear
    public void waitForElementToAppear(By elementBy) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
        highLightElement(elementBy);
    }

    // Wait for element to be clickable
    public void waitForElementToClick(By elementBy) {
        wait.until(ExpectedConditions.elementToBeClickable(elementBy));
        highLightElement(elementBy);
    }

    // Wait for alert to appear
    public void waitForAlertToAppear() {
        alert = wait.until(ExpectedConditions.alertIsPresent());
        if (alert != null) {
            isAlertPresent();
        }
    }

    public boolean isElementPresent(By elementBy) {

        try {
            return driver.findElements(elementBy).size() > 0;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public boolean isElementVisible(By elementBy) {
        try {
            boolean visible = driver.findElement(elementBy).isDisplayed();
            logger.debug("Element {} visible: {}", elementBy, visible);
            return visible;
        } catch (Throwable t) {
            logger.debug("Element {} not visible: {}", elementBy, t.getMessage());
            return false;
        }
    }

    // Highlight Element
    public void highLightElement(By elementBy) {
        try {
            js.executeScript("arguments[0].setAttribute('style','border: 5px solid red;');", driver.findElement(elementBy));
            sleepTime(1);
            // capture a small screenshot of the highlight. This uses the project's Screenshot util
            try {
                //Screenshot.captureScreen(driver, title, testName);
            } catch (Throwable t) {
                logger.warn("Screenshot capture failed while highlighting element: {}", t.getMessage());
            }
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", driver.findElement(elementBy), "");
        } catch (Throwable t) {
            // Do not fail the test just because highlight failed
            logger.debug("Could not highlight element {}: {}", elementBy, t.getMessage());
        }
    }

    // Check alert present
    public boolean isAlertPresent() {
        boolean presentFlag = false;
        try {
            alert = driver.switchTo().alert();
            presentFlag = true;
            alert.accept();
        } catch (NoAlertPresentException ex) {
            logger.debug("No alert present: {}", ex.getMessage());
        }
        return presentFlag;
    }

    // click
    public void click(By elementBy) {
        safeClick(elementBy);
    }

    // clear
    public void clear(By elementBy) {
        waitForElementToClick(elementBy);
        try {
            js.executeScript("arguments[0].value = '';", driver.findElement(elementBy));
        } catch (Throwable t) {
            // fallback to sendKeys if JS fails
            driver.findElement(elementBy).clear();
        }
    }

    // Double click
    public void doubleClick(By elementBy) {
        waitForElementToClick(elementBy);
        actions.doubleClick(driver.findElement(elementBy)).perform();
    }

    // Write text
    public void writeText(By elementBy, String text) {
        waitForElementToAppear(elementBy);
        WebElement el = driver.findElement(elementBy);
        el.clear();
        el.sendKeys(text);
    }

    // Read text
    public String readText(By elementBy) {
        waitForElementToAppear(elementBy);
        return driver.findElement(elementBy).getText();
    }

    // Get element
    public WebElement getElement(By elementBy) {
        return safeFindElement(elementBy);
    }

    // Get title
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String switchToWindow_GetTitle() {
        String parent = driver.getWindowHandle();
        Set<String> s1 = driver.getWindowHandles();
        Iterator<String> I1 = s1.iterator();
        String title = null;
        while (I1.hasNext()) {
            String child_window = I1.next();
            if (!parent.equals(child_window)) {
                driver.switchTo().window(child_window);
                title = driver.switchTo().window(child_window).getTitle();
                driver.close();
            }
        }
        driver.switchTo().window(parent);
        return title;
    }

    // Get elements
    public List<WebElement> getElements(By elementBy) {
        // waitForElementToAppear(elementBy);
        return driver.findElements(elementBy);
    }

    public void keysEnter(By elementBy) {
        waitForElementToClick(elementBy);
        driver.findElement(elementBy).sendKeys(Keys.ENTER);
    }

    public WebElement getSearch(By elementBy, String text) {
        waitForElementToAppear(elementBy);
        driver.findElement(elementBy).sendKeys(text);
        logger.info("Enter the Data in Search textbox:" + elementBy);
        driver.findElement(elementBy).sendKeys(Keys.DOWN);
        logger.info("Selecting Item to down:" + elementBy);
        driver.findElement(elementBy).sendKeys(Keys.ENTER);
        logger.info("Select Item is Enter:" + elementBy);
        return driver.findElement(elementBy);
    }

    public void multiSelectByText(By elementBy, String xPathValue) {
        waitForElementToAppear(elementBy);
        int size = driver.findElements(By.xpath(xPathValue)).size();
        System.out.println(size + " mapsets found");
        boolean element = isElementPresent(By.xpath(xPathValue));
        if (element == true) {
            driver.findElement(By.xpath("(" + xPathValue + ")[" + size + "]")).click();
        } else {

        }
        System.out.println("changes are made to the most recent  mapset");
    }

    // Select by visible text
    public void selectByVisibleText(By elementBy, String value) {
        // waitForElementToAppear(elementBy);
        select = new Select(driver.findElement(elementBy));
        select.selectByVisibleText(value);
    }

    // Select by value
    public void selectByValue(By elementBy, String value) {
        waitForElementToAppear(elementBy);
        select = new Select(driver.findElement(elementBy));
        select.selectByValue(value);
    }

    // Get select value
    public String getSelectedValue(By elementBy) {
        waitForElementToAppear(elementBy);
        Select select = new Select(driver.findElement(elementBy));
        return select.getFirstSelectedOption().getText();
    }

    // Element to be click from the list
    public void elementTobeClickFromList(By elementBy, String value) {
        highLightElement(elementBy);
        List<WebElement> checkBoxes = getElements(elementBy);
        for (WebElement boxes : checkBoxes) {
            String textValues = boxes.getText();
            if (textValues.contains(value)) {

                boxes.click();
                break;
            } else
                throw new RuntimeException(value + " is not available in the list");
        }

    }

    // JavaScript click
    public void javaScriptClick(By elementBy) {
        highLightElement(elementBy);
        js.executeScript("arguments[0].click();", driver.findElement(elementBy));
    }

    // Scroll into view
    public void scrollIntoView(By elementBy) {
        try {
            // Wait for presence/visibility to make the scroll safe
            wait.until(ExpectedConditions.presenceOfElementLocated(elementBy));
            wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
            WebElement el = driver.findElement(elementBy);
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
            sleepTime(1);
        } catch (Throwable t) {
            logger.debug("scrollIntoView failed for {}: {}", elementBy, t.getMessage());
            // last resort: try JS with selector (may fail if element really not present)
            try {
                js.executeScript("document.evaluate(\"" + elementBy.toString().replace("By.", "") + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.scrollIntoView(true);");
            } catch (Throwable ignored) {
                logger.debug("Fallback scrollIntoView failed for {}", elementBy);
            }
        }
    }

    // Scroll page
    public void scroll() {
        js.executeScript("window.scrollBy(0,400)", "");
    }

    // Scroll page
    public void scrollByPage() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    // Thread wait
    public void sleepTime(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Interrupted sleep: {}", e.getMessage());
        }
    }

    // Wait for page ro load
    public void waitForPageToLoad() {
        sleepTime(1);
        String state = (String) js.executeScript("return document.readyState");
        while (!state.equals("complete")) {
            sleepTime(1);
            state = (String) js.executeScript("return document.readyState");
        }
    }
    
    // Wait For 
    public void waitForTimeToLoad(int seconds)
    {
    	try {
    		Thread.sleep(1000 * seconds);
    	} catch (InterruptedException e)
    	{
    		Thread.currentThread().interrupt();
    	}
    }

    // Convert date
    public String convertDate(String inputdate) {
        logger.debug("input date {}", inputdate);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(inputdate);
        } catch (Exception e) {
            logger.warn("Failed to parse date {}: {}", inputdate, e.getMessage());
        }
        return date == null ? null : df.format(date);

    }

    public void maximizeBrowser() {
        driver.manage().window().maximize();

    }

    // Get Screenshot - use project's Screenshot utility instead of ad-hoc file path
    public void takeScreenShot() {
        try {
           // Screenshot.captureScreen(driver, title, testName);
        } catch (Throwable t) {
            logger.warn("Screenshot.captureScreen failed: {}", t.getMessage());
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private WebElement elementFind(String locatorindicator, String locatorvalue) {

        logger.info("find element action is started");
        logger.info("locator indicator is : " + locatorindicator);
        logger.info("locator value is : " + locatorvalue);
        WebElement element = null;
        try {
            if (locatorindicator.equalsIgnoreCase("xpath")) // locator - X path
            {
                element = driver.findElement(By.xpath(locatorvalue));
            } else if (locatorindicator.equalsIgnoreCase("cssSelector")) // locator - CSS Selector
            {
                element = driver.findElement(By.cssSelector(locatorvalue));
            } else if (locatorindicator.equalsIgnoreCase("id")) // locator - ID
            {
                element = driver.findElement(By.id(locatorvalue));
            } else if (locatorindicator.equalsIgnoreCase("name")) // locator - Name
            {
                element = driver.findElement(By.name(locatorvalue));
            } else if (locatorindicator.equalsIgnoreCase("classname")) // locator - ClassName
            {
                element = driver.findElement(By.className(locatorvalue));
            } else if (locatorindicator.equalsIgnoreCase("linktext")) // locator - LinkText
            {
                element = driver.findElement(By.linkText(locatorvalue));
            } else if (locatorindicator.equalsIgnoreCase("partiallinktext")) // locator - partial LinkText
            {
                element = driver.findElement(By.partialLinkText(locatorvalue));
            } else if (locatorindicator.equalsIgnoreCase("tagname")) // locator - tag name
            {
                element = driver.findElement(By.tagName(locatorvalue));
            }
            logger.info("find element action is Completed");

        } catch (Throwable e) {
            logger.info("element is not found:" + e);
        }

        return element;
    }

    public void waitForPageLoaded() {
        // Keep old public method but delegate to the newer waitForPageToLoad implementation
        waitForPageToLoad();
    }

    // small sugar: return list of elements matching a locator (visible or not)
    public List<WebElement> safeFindElements(By by) {
        return driver.findElements(by);
    }

}
