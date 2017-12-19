package com.agnesdora.qa.testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
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



public class Working_PlaceOrderTest extends TestBase {
	LoginPage loginPage = new LoginPage();
	HomePage homePage = new HomePage();
	MyCartPage myCartPage = new MyCartPage();
	ShippingAddressPage shippingAddressPage = new ShippingAddressPage();
	YourCartPage yourCartPage = new YourCartPage();
	OrderCompletePage orderCompletePage = new OrderCompletePage();;
	Reusable reusable = new Reusable();
	TestUtil testUtil = new TestUtil(System.getProperty("user.dir")+ "\\testdata\\JustReleased.xlsx", "TestData");

	
	@DataProvider
	public Object[][] getTestIdList(){
		Object data[][] = TestUtil.getTestIdList("myTestData");
		return data;
	}
	
	@Test(dataProvider="getTestIdList")
	public void orderProducts(String testCaseID) throws IOException {
		TestBase.TESTCASE_ID = testCaseID;
		logger = extent.startTest(testCaseID);
		loginPage.loginUserToWeb(testUtil.getDataByTestCaseId(testCaseID, "userName"), 
				testUtil.getDataByTestCaseId(testCaseID, "password"));
		homePage.clickOnCategoryLink("Just Released");
		
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
		
		extent.endTest(logger);

	}

	@BeforeTest
	public void beforeTest() {		

	}
	
	@BeforeMethod
	@Parameters("browser")
	public void setUp(String browser) {
		openBrowser(browser, "https://loadtest.agnesanddora.com/");
	}
	
	@AfterMethod
	public void tearDown(){
		terminateBrowser();
	}

	@AfterTest
	public void afterTest() {
		extent.flush();
		extent.close();
	}

}
