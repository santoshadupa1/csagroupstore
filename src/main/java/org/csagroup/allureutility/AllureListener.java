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
	        //WebDriver driver=null;
			try {
				driver = DriverManager.getDriver();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // MUST be static thread-safe
	
	        if (driver != null) {
	            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);	
	            Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");
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