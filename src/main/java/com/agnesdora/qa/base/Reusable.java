package com.agnesdora.qa.base;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.LogStatus;

public class Reusable extends TestBase {
	
    /**
     * This method will just verify if element is present or not.
     * @param driver
     * @param locator
     * @return
     */
	public static boolean isElementPresent(WebDriver driver, By by){
		if (driver.findElements(by).size() != 0) {
			return true; // Element present
		} else {
			return false; // Element not present
		}
	}
	/**
	 * This method is for presence of Element
	 * @param locator
	 * @return
	 */
	public static boolean isElementPresent(By by){
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			flag = true;
			return flag;
		} catch (NoSuchElementException e) {
			return flag;
		}
	}
	/**
	 * This method will check for element to be clickable
	 * @param by
	 * @return
	 */
	public static boolean isElementClickable(By by){
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			flag = true;
			return flag;
		} catch (NoSuchElementException e) {
			return flag;
		}
	}
	/**
	 * THis method is will wait for Page to load and wait for new page element to be present
	 * @param n
	 * @param locator
	 * @return
	 */
	public static boolean waitForPageToLoad(int n, By by){
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		boolean flag = false;
		for(int i=1; i<=n; i++){
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			flag = true;
			return flag;
		}
		return flag;
	}
	/**
	 * This is for Thread sleep
	 * @param milliseconds
	 */
	public static void waitFor(long milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method is for Fluent Wait
	 * @param locator
	 */
	public static void myFluentWait(final By by){
		Wait wait = new FluentWait(driver).withTimeout(30, TimeUnit.SECONDS).
				pollingEvery(2, TimeUnit.SECONDS).
				ignoring(NoSuchElementException.class);
		
		Function<WebDriver, WebElement> funciton = new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(by);
				return element;
			}
		};
	}
	/**
	 * This method will selece dropdown value
	 * @param ele
	 * @param type
	 * @param dropvalue
	 */
	public static void selectDropdown(WebElement ele, String type, String dropvalue){
		Select select = new Select(ele);
		
		if(type.equals("value"))		{
			select.selectByValue(dropvalue);
		}else if(type.equals("VisibleTExt")){
			select.selectByVisibleText(dropvalue);
		}else if(type.equals("Index")){
		   int n = Integer.parseInt(dropvalue);
		   select.selectByIndex(n);
		}
	}
	/**
	 * This method will move focus to the element 
	 * @param ele
	 */
	public static void moveToElement(WebElement el){
		Actions actions = new Actions(driver);
		actions.moveToElement(el).build().perform(); 
	}
	/**
	 * This method will element Attribute using java executor
	 * @param by
	 * @param attribute
	 * @param valueToSet
	 */
	public static void setAttributeJava(By by, String attribute, String valueToSet){		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(by);
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attribute, valueToSet);
	}

	public static void verifyClickByText(WebElement element, String txtToVerify) {
		if (element.getText() != null) {
			if ((element.getText()).contains(txtToVerify)) {
				logger.log(LogStatus.PASS, "Sucessfully found " + txtToVerify);
			} else {
				logger.log(LogStatus.FAIL, "Unable to Find " + txtToVerify
						+ ". || Returned Text is " + element.getText());
			}
		}
	}

	public static boolean findTextOnPage(String txtToFind) {
		if (driver.getPageSource().contains(txtToFind)) {
			return true;
		} else {
			return false;
		}
	}
	
}
