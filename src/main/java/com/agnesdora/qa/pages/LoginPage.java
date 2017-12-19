package com.agnesdora.qa.pages;

import org.junit.Assert;
import org.openqa.selenium.By;

import com.agnesdora.qa.base.Reusable;
import com.agnesdora.qa.base.TestBase;
import com.agnesdora.qa.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;


public class LoginPage extends TestBase{
	
	By lnkLogIn = By.xpath("//a[text()='Log In']");
	By txtEmail = By.id("email");
	By txtPassword = By.id("password");
	By btnLogIn = By.xpath("//button[@type = 'submit']");	
	By lnkWelcome = By.xpath("//span[@class = 'account-links']//a[text() = 'Account Settings']//parent::node()//parent::node()[@class='welcome-user']");
	
	/**
	 * This method will login user to website
	 */
	public void loginUserToWeb(String userEmail, String password){
		logger.log(LogStatus.INFO, "Click on Login Link and enter email id and password");
		driver.findElement(lnkLogIn).click();
		Reusable.waitForPageToLoad(2, txtEmail);
		driver.findElement(txtEmail).sendKeys(userEmail);
		driver.findElement(txtPassword).sendKeys(password);
		driver.findElement(btnLogIn).click();
		Reusable.waitForPageToLoad(2, lnkWelcome);
		verifyLogin();
	}
	
	public void verifyLogin(){	
		if(Reusable.isElementPresent(lnkWelcome)){
			String welcomUser = driver.findElement(lnkWelcome).getText();
			if((welcomUser.trim()).contains("WELCOME,")){
				logger.log(LogStatus.PASS, "Log In Sucessfull. User Logged = " + (welcomUser.trim()).substring(8));
			}else{
				logger.log(LogStatus.FAIL, "Log In UnSucessfull.");
				TestUtil.logScreenshotToReport();
				Assert.assertTrue(false);
			}
		}else{
			logger.log(LogStatus.FAIL, "Log In UnSucessfull.");
			TestUtil.logScreenshotToReport();
			Assert.assertTrue(false);
		}
	}		
}
