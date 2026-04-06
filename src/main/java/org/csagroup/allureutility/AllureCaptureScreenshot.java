package org.csagroup.allureutility;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class AllureCaptureScreenshot {
	
	 // 📸 Screenshot (auto attachment)
    @Attachment(value = "{0}", type = "image/png")
    public static byte[] attachScreenshot(String name, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // 🧾 Log as attachment
    public static void attachLog(String message) {
        Allure.addAttachment("Log", new ByteArrayInputStream(message.getBytes()));
    }

    // 🔥 Step-level logging
    public static void step(String message) {
        Allure.step(message);
    }

}
