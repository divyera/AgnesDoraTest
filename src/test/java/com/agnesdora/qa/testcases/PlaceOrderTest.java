package com.agnesdora.qa.testcases;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.agnesdora.qa.base.Reusable;
import com.agnesdora.qa.base.TestBase;
import com.agnesdora.qa.pages.HomePage;
import com.agnesdora.qa.pages.LoginPage;
import com.agnesdora.qa.pages.MyCartPage;
import com.agnesdora.qa.pages.OrderCompletePage;
import com.agnesdora.qa.pages.ShippingAddressPage;
import com.agnesdora.qa.pages.YourCartPage;
import com.agnesdora.qa.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class PlaceOrderTest extends TestBase {
	
	LoginPage loginPage = new LoginPage();
	HomePage homePage = new HomePage();
	MyCartPage myCartPage = new MyCartPage();
	ShippingAddressPage shippingAddressPage = new ShippingAddressPage();
	YourCartPage yourCartPage = new YourCartPage();
	OrderCompletePage orderCompletePage = new OrderCompletePage();;
	Reusable reusable = new Reusable();
	TestUtil testUtil = new TestUtil(System.getProperty("user.dir")+ "\\testdata\\OrderProducts.xlsx", "TestDataAll");
	
	@DataProvider
	public Object[][] getTestIdList(){
		Object data[][] = TestUtil.getTestIdList("myTestData");
		return data;
	}
	
	@Test(dataProvider="getTestIdList")
	public void orderProducts(String testCaseID) throws IOException {
		TestBase.TESTCASE_ID = testCaseID;
		HomePage.expectedProductTotal = 0;
		logger = extent.startTest(testCaseID);
		loginPage.loginUserToWeb(testUtil.getDataByTestCaseId(testCaseID, "userName"), 
				testUtil.getDataByTestCaseId(testCaseID, "password"));
		
		homePage.clickOnCategoryLink(testUtil.getDataByTestCaseId(testCaseID, "category"));
//		homePage.emptyMyCart();
//		homePage.clickOnCategoryLink(testUtil.getDataByTestCaseId(testCaseID, "category"));
		
		String[] arrPrd = testUtil.getDataByTestCaseId(testCaseID, "productInfo").split("\\|");
		for (int i=0; i< arrPrd.length; i++){
			String[] arrPrd2Order = arrPrd[i].toString().split("\\:");
			homePage.addProductToCart(arrPrd2Order[0], arrPrd2Order[1], arrPrd2Order[2]);
		}	
		
		homePage.clickCartLink();
		myCartPage.verifyTotalProduct();
		myCartPage.clickOnCheckout();
		shippingAddressPage.clickOnContinue();
		yourCartPage.verifyGrandTotal();
		yourCartPage.clickOnCreditCard();
		
		yourCartPage.enterCreditCardInfo(testUtil.getDataByTestCaseId(testCaseID, "cardNumber"),
				testUtil.getDataByTestCaseId(testCaseID, "nameOnCard"),
				testUtil.getDataByTestCaseId(testCaseID, "cvv"),
				testUtil.getDataByTestCaseId(testCaseID, "expDate"));
		
		yourCartPage.clickOnPlaceOrder();
		orderCompletePage.captureOrderInfo();
	}

	@BeforeTest
	public void beforeTest() {		

	}
	
	@BeforeMethod
	public void setUp(){
		openBrowser("chrome", "https://loadtest.agnesanddora.com/");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result){
		if(ITestResult.FAILURE==result.getStatus()){
			TestUtil.logScreenshotToReport();
			if (result.getThrowable() != null) {
				logger.log(LogStatus.FAIL, "Test Failed due to: " + (result.getThrowable()).toString());
			}
		}
		extent.endTest(logger);
		terminateBrowser();
	}

	@AfterTest
	public void afterTest() {
		extent.flush();
		extent.close();
	}
	
	public void test()
	{
		
	}

}
