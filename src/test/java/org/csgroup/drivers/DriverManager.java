package org.csgroup.drivers;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.csagroup.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.aventstack.chaintest.plugins.ChainTestListener;

//@Listeners(ChainTestListener.class)
public class DriverManager {

	    protected static WebDriver driver;
	    public static Properties prop;

	    protected LoginPage lp;
	    public static WebDriver getDriver() throws IOException {

	        if (driver == null) {

	            prop = new Properties();
	            String filePath = System.getProperty("user.dir") +"/src/test/resources/configurations/config.properties";

	            FileInputStream fis = new FileInputStream(filePath);
	            prop.load(fis);

	           // String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
	            String rawBrowser = System.getProperty("browser") != null 
	                    ? System.getProperty("browser") 
	                    : prop.getProperty("browser");

	            String browserName = rawBrowser.toLowerCase().split("-")[0];   // chrome
	            String mode = rawBrowser.toLowerCase().contains("headless") 
	                    ? "headless" 
	                    : "headed";
	            switch (browserName) {

	                case "chrome":
	                    ChromeOptions options = new ChromeOptions();
	                    Map<String, Object> prefs = new HashMap<String, Object>();
	        		    prefs.put("credentials_enable_service", false);
	        		    prefs.put("password_manager_enabled", false);
	        		    Map<String, Object> profile = new HashMap<String, Object>();
	        		    profile.put("password_manager_leak_detection", false);
	        		    prefs.put("profile", profile);
	        		    options.setExperimentalOption("prefs", prefs);
	        		    if (mode.contains("headless")) {
	        		        options.addArguments("--headless=new");
	        		        options.addArguments("--window-size=1920,1080");
	        		        options.addArguments("--disable-gpu");
	        		        options.addArguments("--no-sandbox");
	        		        options.addArguments("--disable-dev-shm-usage");
	        		    }
	        		    driver = new ChromeDriver(options); // ✅ ONLY ONCE
	                    break;

	                case "firefox":
	                    FirefoxOptions firefoxOptions = new FirefoxOptions();
	                    if (mode.equals("headless")) {
	                        firefoxOptions.addArguments("--headless");
	                    }
	                    driver = new FirefoxDriver(firefoxOptions);
	                    break;

	                default:
	                    throw new RuntimeException("Invalid browser: " + browserName);
	            }

	            driver.manage().window().maximize();
	            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        }

	        return driver;
	    }

	    public static String getURL() {
	    	String env = System.getProperty("env") != null ? System.getProperty("env") : prop.getProperty("env");
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
	// For testing
	public static void main(String[] args) throws IOException {
		DriverManager.getDriver().get("https://b2buat-csastandards.cs93.force.com/store");
	}

	protected void waitForVisibility(WebElement element) throws Error {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(element));
	}

//	// Excel Reader Method
//	public String[][] getData(String ExcelName, String sheetName) {
//		// /uiAutoframework/src/main/java/com/test/Autoframework/uiAutoframework/data/TestData.xlsx
//		String path = System.getProperty("user.dir") + "/src/main/java/com/csagroup/resources/" + ExcelName;
//		excel = new ExcelReader(path);
//		String[][] data = excel.getDataFromSheet(sheetName, ExcelName);
//		return data;
//	}
	
	@BeforeMethod
	public void setup() throws IOException {

	    driver = DriverManager.getDriver();
	    driver.get(DriverManager.getURL());
	    lp = new LoginPage(driver);
	    lp.waitForPageToLoad();
	    lp.clickAcceptAllCookies();


         String environment = System.getProperty("env") != null ? System.getProperty("env") : prop.getProperty("env");
         environment = environment.split("#")[0].trim().toLowerCase();


	    switch(environment)
	    {
	        case "stage":
	            lp.securityCodeVerification(prop.getProperty("securitycode"));
	            break;
	        case "prod":
	            System.out.println("Prod env → skipping security code");
	            break;
	        default:
	            throw new RuntimeException("Invalid environment: " + environment);
	    }
	}

	@AfterMethod
	public void tearDown() {
	    driver.quit();
	}
}
