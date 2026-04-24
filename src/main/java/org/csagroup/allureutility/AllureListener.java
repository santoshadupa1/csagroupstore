package org.csagroup.allureutility;


import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.csgroup.drivers.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Allure;


public class AllureListener extends DriverManager implements ITestListener {

	   //AllureCaptureScreenshot allureUtil;
		@Override
		public void onTestFailure(ITestResult result) {
		    try {
		        driver = getDriver();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    if (driver != null) {
		        // Fix 1: Ensure window has a proper size (critical for headless)
		        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));	
		        // Fix 2: Small pause to let rendering pipeline flush
		        try { Thread.sleep(500); } catch (InterruptedException ignored) {}	
		        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		        Allure.addAttachment("Failure Screenshot", "image/png",
		                new ByteArrayInputStream(screenshot), ".png");
		    }
		    Allure.addAttachment("Failure Log", result.getThrowable().toString());
		}


	    @Override
	    public void onTestSuccess(ITestResult result) {
	    	AllureCaptureScreenshot.attachLog("Test Passed: " + result.getName());
	    }

	    @Override
	    public void onTestStart(ITestResult result) {
	    	AllureCaptureScreenshot.step("Starting Test: " + result.getName());
	    }
	
}