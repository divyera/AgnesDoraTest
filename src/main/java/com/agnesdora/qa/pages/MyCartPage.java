package com.agnesdora.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.agnesdora.qa.base.Reusable;
import com.agnesdora.qa.base.TestBase;
import com.agnesdora.qa.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class MyCartPage extends TestBase{
	
	By btnCheckout = By.xpath("//a[@class= 'checkout-link btn']");
	By txtActualTotalAmount = By.xpath("//td[@class = 'total-amount']");
	/**
	 * This method click on Category Link
	 */
	public void clickOnCheckout(){
		logger.log(LogStatus.INFO, "Click on Checkout Link.");
		Reusable.isElementClickable(btnCheckout);
		Reusable.moveToElement(driver.findElement(btnCheckout));
		driver.findElement(btnCheckout).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	/**
	 * This method captures My Cart Total amount
	 * @return mycart total amount
	 */
	public String getMyCartTotal(){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(btnCheckout));
		String actualTotal = driver.findElement(txtActualTotalAmount).getText();
		logger.log(LogStatus.INFO, "My Cart Total = " + actualTotal);
		return actualTotal;
	}
	public boolean bolMyCartTotal(){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(btnCheckout));
		String actualTotal = driver.findElement(txtActualTotalAmount).getText();
		if(actualTotal == "$0.00"){
			TestUtil.logScreenshotToReport();
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * This method captures My Cart Product Total
	 * @param sizeProdId = Size Product Code
	 * @return mycart Product amount
	 */
	public String getMyCartProdcutTotal(String sizeProdId){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(btnCheckout));
		String actualProductTotal = driver.findElement(By.xpath("//tr[@id = 'row-'" + sizeProdId + "']/td[@class = 'product-price']")).getText();
		return actualProductTotal;
	}
	/**
	 * This method captures My Cart Product Quantity
	 * @param sizeProdId = Size Product Code
	 * @return mycart Product Qty
	 */
	public String getMyCartProdcutQty(String sizeProdId){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(btnCheckout));
		String actualProductQty = driver.findElement(By.xpath("//tr[@id = 'row-'" + sizeProdId + "']/td[1]")).getText();
		return actualProductQty;
	}
	/**
	 * This method captures My Cart Product Name
	 * @param sizeProdId = Size Product Code
	 * @return mycart Product Name
	 */
	public String getMyCartProdcutName(String sizeProdId){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(btnCheckout));
		String actualProductName = driver.findElement(By.xpath("//tr[@id = 'row-'" + sizeProdId + "']/td[@class = 'product-price']/div")).getText();
		return actualProductName;
	}
	
	public void verifyTotalProduct(){
		double actualProdTotal = Double.parseDouble(getMyCartTotal().substring(1));
		if(actualProdTotal == HomePage.expectedProductTotal){
			logger.log(LogStatus.PASS, "Product Total matched Expected.");
		}else{
			logger.log(LogStatus.FAIL, "Product Total did not match. Expected Product Total = " + HomePage.expectedProductTotal + " || Actual Product Total = " + actualProdTotal);
		}	
		Assert.assertTrue(bolMyCartTotal());
	}

}
