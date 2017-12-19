package com.agnesdora.qa.pages;

import org.openqa.selenium.By;

import com.agnesdora.qa.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class OrderCompletePage extends TestBase{
	
	public void captureOrderInfo(){
		logger.log(LogStatus.INFO, "Order Complete Page - Capture Order Info.");
		logger.log(LogStatus.INFO, driver.findElement(By.xpath("//label[text() = 'Order ID:']//parent::node()")).getAttribute("innerHTML"));
		logger.log(LogStatus.INFO, driver.findElement(By.xpath("//label[text() = 'Order Date']//parent::node()")).getAttribute("innerHTML"));
		logger.log(LogStatus.INFO, driver.findElement(By.xpath("//label[text() = 'Shipping Address']//parent::node()")).getAttribute("innerHTML"));
		logger.log(LogStatus.INFO, driver.findElement(By.xpath("//label[text() = 'Billing:']//parent::node()")).getAttribute("innerHTML"));
		
	}

}
