package org.csgroup.drivers;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.pages.LoginPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.qameta.allure.Step;

public class DriverManager {

    // ✅ NOT static — each test gets its own driver instance
    public WebDriver driver;
    public static Properties prop;

    protected LoginPage lp;

    // ================================
    // SINGLE SOURCE OF TRUTH (ENV)
    // ================================
    public static String getEnv() {
        String env = System.getProperty("env") != null
                ? System.getProperty("env")
                : prop.getProperty("env");
        return env.split("#")[0].trim().toLowerCase();
    }

    public WebDriver getDriver() throws IOException {

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
                ? "headless" : "headed";

        boolean isCI = System.getenv("CI") != null;
        boolean useHeadless = isCI || mode.equals("headless");

        switch (browserName) {

            case "chrome":

                ChromeOptions options = new ChromeOptions();
                // ✅ Disable password manager popups
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);
                Map<String, Object> profile = new HashMap<>();
                profile.put("password_manager_leak_detection", false);
                prefs.put("profile", profile);
                options.setExperimentalOption("prefs", prefs);

                // ✅ Core stability flags
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--remote-allow-origins=*");
                options.setExperimentalOption("excludeSwitches", 
                    Arrays.asList("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);

                if (useHeadless) {
                    options.addArguments("--headless=new");
                    // ✅ FIX 1: Only window-size, remove --start-maximized conflict
                    options.addArguments("--window-size=1920,1080");
                    // ✅ FIX 2: Updated user-agent matching actual Chrome version
                    options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                            + "AppleWebKit/537.36 (KHTML, like Gecko) "
                            + "Chrome/147.0.0.0 Safari/537.36");
                    // ✅ Extra flags for CI runner stability
                    options.addArguments("--disable-setuid-sandbox");
                    options.addArguments("--disable-software-rasterizer");
                    options.addArguments("--disable-background-networking");
                    options.addArguments("--disable-default-apps");
                    options.addArguments("--no-first-run");
                    options.addArguments("--no-zygote");        // ✅ helps on Azure runners
                    options.addArguments("--single-process");   // ✅ helps on limited RAM runners
                } else {
                    options.addArguments("--start-maximized");
                }

                driver = new ChromeDriver(options);

                // ✅ FIX 3: Set window size after driver init (not before page load)
                if (useHeadless) {
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                }

                break;

            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                firefoxOptions.addPreference("privacy.trackingprotection.enabled", true);

                if (useHeadless) {
                    firefoxOptions.addArguments("-headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }

                driver = new FirefoxDriver(firefoxOptions);

                if (useHeadless) {
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                }

                break;

            default:
                throw new RuntimeException("Invalid browser: " + browserName);
        }

        // ✅ FIX 4: Remove implicitlyWait — use only explicit waits in page classes
        // driver.manage().timeouts().implicitlyWait(...) — REMOVED

        // ✅ FIX 5: Set page load timeout instead
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        return driver;
    }

    public static String getURL() {
        switch (getEnv()) {
            case "stage":
                AllureCaptureScreenshot.step("Environment URL is:" + prop.getProperty("stageUrl"));
                System.out.println("Environment URL is:" + prop.getProperty("stageUrl"));
                return prop.getProperty("stageUrl");
            case "prod":
                System.out.println("Environment URL is:" + prop.getProperty("prodUrl"));
                AllureCaptureScreenshot.step("Environment URL is:" + prop.getProperty("prodUrl"));
                return prop.getProperty("prodUrl");
            default:
                throw new RuntimeException("Invalid environment: " + getEnv());
        }
    }

    @BeforeMethod
    @Step("Setup browser and launch application")
    public void setup() throws IOException {

        driver = getDriver();
        AllureCaptureScreenshot.attachLog("Browser: " + prop.getProperty("browser"));
        AllureCaptureScreenshot.attachLog("Environment: " + prop.getProperty("env"));
        driver.get(DriverManager.getURL());
        // ✅ FIX 5: waitForDocumentReady AFTER driver.get() — now it actually means something
        waitForDocumentReady(driver);
        lp = new LoginPage(driver);
        lp.waitForPageToLoad();
        AllureCaptureScreenshot.step("Accepting cookies");
        lp.clickAcceptAllCookies();

        switch (getEnv()) {
            case "stage":
                AllureCaptureScreenshot.step("Entering security code");
                lp.securityCodeVerification(prop.getProperty("securitycode"));
                break;
            case "prod":
                AllureCaptureScreenshot.step("Prod env → skipping security code");
                System.out.println("Prod env → skipping security code");
                break;
            default:
                throw new RuntimeException("Invalid environment: " + getEnv());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ✅ FIX 6: Increase timeout for CI — 60s instead of 30s
    protected void waitForDocumentReady(WebDriver driver) {
        boolean isCI = System.getenv("CI") != null;
        int timeout = isCI ? 60 : 30;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(d -> ((JavascriptExecutor) d)
                            .executeScript("return document.readyState")
                            .equals("complete"));
        } catch (Exception e) {
            System.out.println("WARN: document.readyState timeout after " 
                + timeout + "s — continuing anyway");
        }
    }

    protected void waitForVisibility(WebElement element) {
        boolean isCI = System.getenv("CI") != null;
        int timeout = isCI ? 60 : 30;
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOf(element));
    }
}