package com.agnesdora.qa.pages;

import org.openqa.selenium.By;

import com.agnesdora.qa.base.Reusable;
import com.agnesdora.qa.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class ShippingAddressPage extends TestBase{
	
	/**
	 * This method click on Category Link
	 */
	public void clickOnContinue(){
		logger.log(LogStatus.INFO, "Shipping Address Page - Click on Continue Button.");
		By by = By.id("proceed-with-shipping");
		Reusable.isElementClickable(by);
		Reusable.moveToElement(driver.findElement(by));
		driver.findElement(by).click();
		Reusable.waitFor(10000);	
	}

}
