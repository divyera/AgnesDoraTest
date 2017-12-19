package com.agnesdora.qa.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.agnesdora.qa.base.Reusable;
import com.agnesdora.qa.base.TestBase;
import com.agnesdora.qa.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class HomePage extends TestBase{
	
	By lnkCart = By.xpath("//a[@title= 'Cart']");
	public static String sizeProduct = null;
	public static double expectedProductTotal = 0.00;
	/**
	 * This method click on Category Link
	 * @param txtCat = Category Name
	 */
	public void clickOnCategoryLink(String txtCat){
		logger.log(LogStatus.INFO, "Click on "+ txtCat +" Link.");
		driver.findElement(By.xpath("//a[@title='" + txtCat + "']")).click();
		By by = By.xpath("//div[@class = 'product_right_main']//span[@class = 'category-title']");
		Reusable.waitForPageToLoad(2, by);
		Reusable.verifyClickByText(driver.findElement(by), txtCat.toUpperCase());
	}
	public void addProductToCart(String prodId, String size, String qty){
		logger.log(LogStatus.INFO, "Order "+ qty +" QTY of "+ getProductName(prodId) +" -" + size);
		int beforeProdCt = getMyCartQtyCount();
		selectProductWithSize(prodId, size);
		selectQtyByProduct(getSizeProduct(prodId, size), qty);
		logger.log(LogStatus.INFO, "Size Procuct Code "+ sizeProduct );
		clickAddToCart(sizeProduct);
//		Reusable.waitFor(10000);
		int afterProdCt = getMyCartQtyCount();
		if(afterProdCt == (beforeProdCt + Integer.parseInt(qty))){
			logger.log(LogStatus.PASS, "Product was added to cart");
		}else{
			logger.log(LogStatus.FAIL, "Product was not added to cart");
			TestUtil.logScreenshotToReport();
		}
		double prdPrice = Double.parseDouble(getProductPrice(prodId));
		expectedProductTotal += (prdPrice * Integer.parseInt(qty)); 
		logger.log(LogStatus.INFO, "expectedProductTotal = "+ expectedProductTotal);
//		Assert.assertEquals(String.valueOf(afterProdCt), String.valueOf((beforeProdCt + Integer.parseInt(qty))));
	}
	/**
	 * This method will Select product based on Product code and size
	 * @param prodId = Product Code
	 * @param size = String Size
	 */
	public void selectProductWithSize(String prodId, String size){
		By by = By.xpath("//div[@data-id='"+ prodId + "']//div[contains(@class,'purchase-list')]//a//span[text()='"+ size + "']");
		Reusable.isElementClickable(by);
		Reusable.moveToElement(driver.findElement(By.xpath("//div[@data-id='"+ prodId + "']//div[contains(@class,'purchase-list')]")));
		driver.findElement(by).click();
	}
	/**
	 * This function captures individual product Price
	 * @param prodId = Product Code
	 * @return Product Price
	 */
	public String getProductPrice(String prodId){
		String txtProductCost = driver.findElement(By.xpath("//div[@data-id='"+ prodId + "']")).getAttribute("data-price");
		return txtProductCost;
	}
	/**
	 * This method will return product code for Size item
	 * @param prodId
	 * @param size
	 * @return
	 */
	public String getSizeProduct(String prodId, String size){
		sizeProduct = driver.findElement(By.xpath("//div[@data-id='"+ prodId + "']//div[contains(@class,'purchase-list')]//a//span[text()='"+ size + "']//parent::node()")).getAttribute("data-id");
		return sizeProduct;
	}
	/**
	 * This method will return Product Name
	 * @param prodId
	 * @return
	 */
	public String getProductName(String prodId){
		String txtProductName = driver.findElement(By.xpath("//div[@data-id='"+ prodId + "']")).getAttribute("data-name");
		return txtProductName;
	}
	/**
	 * This method will Select Quantity by product ID
	 * @param sizeProdId = Size Product Code
	 * @param qty = String Quantity
	 */
	public void selectQtyByProduct(String sizeProdId, String qty){
		Reusable.setAttributeJava(By.xpath("//div[@id= 'modal-"+ sizeProdId + "']/div[@class = 'controls']/input"), "value", qty);	
		Reusable.waitFor(500);
		if(driver.findElement(By.xpath("//div[@id= 'modal-"+ sizeProdId + "']/div[@class = 'controls']/input")).getText() != qty){
			Reusable.setAttributeJava(By.xpath("//div[@id= 'modal-"+ sizeProdId + "']/div[@class = 'controls']/input"), "value", qty);	
			Reusable.waitFor(500);			
		}
	}
	/**
	 * This method will click on Add/update Cart Button
	 * @param sizeProdId = Size Product Code
	 */
	public void clickAddToCart(String sizeProdId){		
		driver.findElement(By.xpath("//div[@id= 'modal-"+ sizeProdId + "']/div[@class = 'controls']//button[3]")).click();
		if(Reusable.isElementPresent(By.xpath("//div[@id= 'modal-"+ sizeProdId + "']/div[contains(@class,'modal_message visible')]")) == false){
			logger.log(LogStatus.FAIL, "Unable to add Product to Cart.");
			TestUtil.logScreenshotToReport();
			Assert.assertTrue(false);
		}
	}
	/**
	 * This method will click on Cart link
	 */
	public void clickCartLink(){
		logger.log(LogStatus.INFO, "Click on My Cart Link.");
		driver.findElement(lnkCart).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement el = driver.findElement(By.xpath("//div[@class= 'order-summary popup-content']//h2"));
		Reusable.verifyClickByText(el, "MY CART");
	}
	/**
	 * This method with get count of Total available product on page
	 * @return product count total
	 */
	public int getTotalProductCount(){
		List<WebElement> countDiv = driver.findElements(By.cssSelector(".products div"));
		int count = countDiv.size();
		
		return count;
	}
	/**
	 * This method returns Total Quantity from My Cart
	 * @return
	 */
	public int getMyCartQtyCount(){	
		System.out.println("getMyCartQtyCount... Get text");
		String txtGetText = driver.findElement(By.xpath("//a[@title= 'Cart']//span")).getText();
		System.out.println("getMyCartQtyCount... Get text = " + txtGetText);
		if(txtGetText == null || txtGetText.isEmpty()){
			txtGetText = "0";
		}
		System.out.println("getMyCartQtyCount... Get text = " + txtGetText);
		return Integer.parseInt(txtGetText);
	}
	public void emptyMyCart(){
		if(getMyCartQtyCount() > 0 ){
			driver.findElement(lnkCart).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> rows = driver.findElements(By.xpath("//table[@class='order-popup-table']/tbody/tr"));
			System.out.println(rows.size());
			for(int i=1; i<rows.size(); i++){
				driver.findElement(By.xpath("//table[@class='order-popup-table']/tbody/tr[" + i + "]/td[5]/a")).click();
			}
			Reusable.waitFor(2000);
			driver.findElement(By.xpath("//div[@class='order-summary popup-content']//button")).click();
			Reusable.waitFor(10000);			
		}	
	}

}
