package org.csgroup.drivers;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Arrays;

import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Dimension;
import org.testng.annotations.*;

import io.qameta.allure.Step;

public class DriverManager {

    public static WebDriver driver;   // ❗ NOT static
    public static Properties prop;

    protected LoginPage lp;

    public static WebDriver getDriver() throws IOException {

        prop = new Properties();
        String filePath = System.getProperty("user.dir")
                + "/src/test/resources/configurations/config.properties";

        FileInputStream fis = new FileInputStream(filePath);
        prop.load(fis);

        String rawBrowser = System.getProperty("browser") != null
                ? System.getProperty("browser")
                : prop.getProperty("browser");

        String browserName = rawBrowser.toLowerCase().split("-")[0];
        String mode = rawBrowser.toLowerCase().contains("headless")
                ? "headless"
                : "headed";

        boolean isCI = System.getenv("CI") != null;

        switch (browserName) {

            case "chrome":

                ChromeOptions options = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);

                Map<String, Object> profile = new HashMap<>();
                profile.put("password_manager_leak_detection", false);
                prefs.put("profile", profile);

                options.setExperimentalOption("prefs", prefs);

                // Recommended extra flags for headless stability and to reduce automation detection
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);

                // Provide a common desktop user-agent so headless more closely matches headed behavior
                options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100 Safari/537.36");

                // 🔥 CI-safe config
                if (isCI || mode.equals("headless")) {
                    // New headless flag (for newer Chrome versions)
                    options.addArguments("--headless=new");
                    // Explicit window size helps element coordinates and layout match headed runs
                    options.addArguments("--window-size=1920,1080");
                    // Also ask Chrome to start maximized when possible
                    options.addArguments("--start-maximized");
                } else {
                    // headed runs: prefer starting maximized
                    options.addArguments("--start-maximized");
                }

                driver = new ChromeDriver(options);

                // Ensure consistent viewport in headless where maximize() may not work
                try {
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                } catch (Exception ignored) {
                    // some driver setups may throw; ignore and continue
                }

                // Wait for document ready before returning driver to tests
                waitForDocumentReady(driver);
                break;

            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                // Minor tweaks for headless stability
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                firefoxOptions.addPreference("privacy.trackingprotection.enabled", true);

                if (isCI || mode.equals("headless")) {
                    // use headless and set window dimensions
                    firefoxOptions.addArguments("-headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }

                driver = new FirefoxDriver(firefoxOptions);

                try {
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                } catch (Exception ignored) {
                }

                waitForDocumentReady(driver);
                break;

            default:
                throw new RuntimeException("Invalid browser: " + browserName);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Maximize may be a no-op in headless; we already set size above for consistency
        try {
            driver.manage().window().maximize();
        } catch (Exception ignored) {
        }

        return driver;
    }

    private static void waitForDocumentReady(WebDriver driver) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            // timeout or other issue; don't fail driver creation — tests can still perform their own waits
        }
    }

    public static String getURL() {
    	String env = System.getProperty("env") != null
                ? System.getProperty("env")
                : prop.getProperty("env");

        env = env.split("#")[0].trim().toLowerCase();
        switch (env) {
            case "stage":
            	AllureCaptureScreenshot.step("Environment URL is:" + prop.getProperty("stageUrl"));
            	System.out.println("Environment URL is:" + prop.getProperty("stageUrl"));
                return prop.getProperty("stageUrl");
            case "prod":
            	System.out.println("Environment URL is:" + prop.getProperty("stageUrl"));
            	AllureCaptureScreenshot.step("Environment URL is:" + prop.getProperty("prodUrl"));
                return prop.getProperty("prodUrl");
            default:
                throw new RuntimeException("Invalid environment: " + env);
        }
    }

    @BeforeMethod
    @Step("Setup browser and launch application")
	public void setup() throws IOException {

	    driver = DriverManager.getDriver();
	    AllureCaptureScreenshot.attachLog("Browser: " + prop.getProperty("browser"));
	    AllureCaptureScreenshot.attachLog("Environment: " + prop.getProperty("env"));
	    driver.get(DriverManager.getURL());
	    lp = new LoginPage(driver);
	    lp.waitForPageToLoad();
	    AllureCaptureScreenshot.step("Accepting cookies");
	    lp.clickAcceptAllCookies();

	    String environment = System.getProperty("env") != null
	            ? System.getProperty("env")
	            : prop.getProperty("env");

	    environment = environment.split("#")[0].trim().toLowerCase();
	    switch(environment)
	    {
	        case "stage":
	        	AllureCaptureScreenshot.step("Entering security code");
	            lp.securityCodeVerification(prop.getProperty("securitycode"));
	            break;
	        case "prod":
	        	AllureCaptureScreenshot.step("Prod env → skipping security code");
	            System.out.println("Prod env → skipping security code");
	            break;
	        default:
	            throw new RuntimeException("Invalid environment: " + environment);
	    }
	}


    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();   // ✅ clean close per test
        }
    }

    protected void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(element));
    }
}