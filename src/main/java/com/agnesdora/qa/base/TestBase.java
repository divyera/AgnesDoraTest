package com.agnesdora.qa.base;

import java.awt.RenderingHints.Key;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.agnesdora.qa.util.TestUtil;
import com.agnesdora.qa.util.WebEventListener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class TestBase {
	
	static ITestResult result;
	
	public static WebDriver driver;
	public static Properties prop;	
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static String TESTCASE_ID;
//	public  static EventFiringWebDriver e_driver;
//	public static WebEventListener eventListener;
	
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/agnesdora"
					+ "/qa/config/config.properties");
			prop.load(ip);
			extent = new ExtentReports(System.getProperty("user.dir") + "\\reports\\Reports_" + TestUtil.getDateAndTime() + ".html", true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will launch Browser
	 */
	public static void openBrowser(String browserName, String url){
		
		if(browserName.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\driver\\chromedriver.exe");	
			driver = new ChromeDriver(); 
			url = url.replace("loadtest", "homer:plowking@loadtest");
		}else if(browserName.equalsIgnoreCase("ff")){
			System.setProperty("webdriver.firefox.marionette", "C:\\Selenium\\driver\\geckodriver.exe");	
			driver = new FirefoxDriver(); 
		}else if(browserName.equalsIgnoreCase("ie")) { 
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\driver\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
//		e_driver = new EventFiringWebDriver(driver);
//		// Now create object of EventListerHandler to register it with EventFiringWebDriver
//		eventListener = new WebEventListener();
//		e_driver.register(eventListener);
//		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(url);
		if(browserName.equalsIgnoreCase("ie")) {
			driver.switchTo().alert().authenticateUsing(new UserAndPassword("homer", "plowking"));
		}else if(browserName.equalsIgnoreCase("ff")){
			Alert alert = driver.switchTo().alert();
			alert.authenticateUsing(new UserAndPassword("homer", "plowking"));
			alert.accept();
		}
	}
	
	public void windowAuthentication(){
//		Robot robot = new Robot();
//		robot.keyPress(Keys);
		
		//ChromeOptions- Browser profile creation
		//IEProfile
		//FirefoxProfile
		//profileini 
		//Selenium Grid
		//AutoIt
		//Jenkins WAr
		//Tomcat server
	}
	/**
	 * This method will Close Browser
	 */
	public static void terminateBrowser(){
		driver.quit();	
	}
}
