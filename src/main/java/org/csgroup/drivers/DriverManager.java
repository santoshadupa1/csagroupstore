package org.csgroup.drivers;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.csagroup.allureutility.AllureCaptureScreenshot;
import org.csagroup.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
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

                // 🔥 CI-safe config
                if (isCI || mode.equals("headless")) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--window-size=1920,1080");
                }

                driver = new ChromeDriver(options);
                break;

            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (isCI || mode.equals("headless")) {
                    firefoxOptions.addArguments("--headless");
                }

                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new RuntimeException("Invalid browser: " + browserName);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;
    }

    public static String getURL() {
    	String env = System.getProperty("env") != null
                ? System.getProperty("env")
                : prop.getProperty("env");

        env = env.split("#")[0].trim().toLowerCase();
        switch (env) {
            case "stage":
                return prop.getProperty("stageUrl");
            case "prod":
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
	        	AllureCaptureScreenshot.attachLog("Prod env → skipping security code");
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