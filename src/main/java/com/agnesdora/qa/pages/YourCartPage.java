package com.agnesdora.qa.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.agnesdora.qa.base.TestBase;
import com.agnesdora.qa.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class YourCartPage extends TestBase{

	/**
	 * This method click on Category Link
	 */
	public void clickOnPlaceOrder(){
		logger.log(LogStatus.INFO, "Your Cart Page - Click on Place Order.");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-place-order")));
		WebElement element = driver.findElement(By.id("btn-place-order"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();  
		el.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void clickOnCreditCard(){
		driver.findElement(By.xpath("//div[@class='payment_option']//input[@id='CC']//parent::node()//label")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void enterCreditCardInfo(String cardNum, String cardHolderName, String cardCVV, String cardExpDate){
		driver.findElement(By.id("card_no")).sendKeys(cardNum);
		driver.findElement(By.id("card_holder")).sendKeys(cardHolderName);
		driver.findElement(By.id("cvc")).sendKeys(cardCVV);
		driver.findElement(By.id("expiry")).sendKeys(cardExpDate);
	}
	
	public void verifyGrandTotal(){
		String txtGrandTotal = driver.findElement(By.xpath("//td[@class='grandtotal']//strong")).getText();
		if(txtGrandTotal.equalsIgnoreCase("$0.00")){
			logger.log(LogStatus.FAIL, "Your Cart Page Total is $0.00");
			TestUtil.logScreenshotToReport();
			Assert.assertTrue(false);
		}
	}

}
