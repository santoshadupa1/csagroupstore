package org.csagroup.allureutility;

import io.qameta.allure.Attachment;

import org.csgroup.drivers.DriverManager;
import org.openqa.selenium.*;
import org.testng.*;

public class AllureListener implements ITestListener {

	AllureCaptureScreenshot allureUtil;
	
	    @Override
	    public void onTestFailure(ITestResult result) {

	        WebDriver driver = DriverManager.driver;

	        if (driver != null) {
	        	AllureCaptureScreenshot.attachScreenshot("Failure Screenshot", driver);
	        }

	        AllureCaptureScreenshot.attachLog("Test Failed: " + result.getName());
	        AllureCaptureScreenshot.attachLog("Exception: " + result.getThrowable());
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