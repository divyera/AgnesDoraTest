package com.agnesdora.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.agnesdora.qa.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	
	public static String TESTDATA_SHEET_PATH;
	
	public FileInputStream file;
	public FileOutputStream fileOut;
	
	static Workbook book;
	static Sheet sheet;
	static Row row;
	static Cell cell;
	
	
	public TestUtil(String filePath, String sheetName){
		TESTDATA_SHEET_PATH = filePath;
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);	
	}
	
	public static Object[][] getTestData(String sheetName) {
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				//System.out.println(data[i][k]);
			}
		}
		return data;
	}
	
	public static Object[][] getTestIdList(String sheetName) {
		System.out.println(sheet.getLastRowNum() + "--------" + sheet.getRow(0).getLastCellNum());
		Object[][] data = new Object[sheet.getLastRowNum()][1];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < 1; k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				//System.out.println(data[i][k]);
			}
		}
		return data;
	}
	/**
	 * This method will return cell data based on row number and column name
	 * @param rowNum
	 * @param colName
	 * @return
	 */
	public String getData(int rowNum, String colName){
		int colNum = 0;
		String txtDataVal;
		row = sheet.getRow(0);
		//find Column number
		for(int i=0;i<=row.getLastCellNum();i++){
			if(row.getCell(i).getStringCellValue().equalsIgnoreCase(colName)){
				colNum = i;
				break;
			}
		}
		//Goto appropriate row
		row = sheet.getRow(rowNum-1);
		//return appropriate row and column value
		txtDataVal = row.getCell(colNum).getStringCellValue();
		return txtDataVal;		
	}
	/**
	 * This method will update excel with provided data based on row and cell number.
	 * @param rowNum
	 * @param colName
	 * @param colData
	 */
	public void setData(int rowNum, String colName, String colData){
		int colNum = 0;
		row = sheet.getRow(0);
		//find Column number
		for(int i=0;i<=row.getLastCellNum();i++){
			if(row.getCell(i).getStringCellValue().equalsIgnoreCase(colName)){
				colNum = i;
				break;
			}
		}
		//Goto appropriate row
		row = sheet.getRow(rowNum-1);
		//Set data to row and column
		row.getCell(colNum).setCellValue(colData);
		
	    try {
			fileOut = new FileOutputStream(TESTDATA_SHEET_PATH);
			book.write(fileOut);
		    fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	/**
	 * This method will find data based on TestCaseID column and Column Name.  This Column should be first one in data sheet.
	 * @param testCase
	 * @param colName
	 * @return
	 */
	public String getDataByTestCaseId(String testCase, String colName){
		int colNum = 0;
		int rowNum = 0;
		String txtDataVal;
		row = sheet.getRow(0);
		//find Column number
		for(int i=0;i<=row.getLastCellNum();i++){
			if(row.getCell(i).getStringCellValue().equalsIgnoreCase(colName)){
				colNum = i;
				break;
			}
		}
		//find Row number
		for(int i=1;i<=sheet.getLastRowNum();i++){
			row = sheet.getRow(i);
			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(testCase)){
				rowNum = i;
				break;
			}
		}
		//Goto appropriate row
		row = sheet.getRow(rowNum);
		//return appropriate row and column value
		txtDataVal = row.getCell(colNum).getStringCellValue();
		return txtDataVal;
	}
	/**
	 * This method will find data based on TestCaseID, Column Name and Provided Data.  This Column should be first one in data sheet.
	 * @param testCase
	 * @param colName
	 * @param colData
	 * @return
	 */
	public void setDataByTestCaseId(String testCase, String colName, String colData){
		int colNum = 0;
		int rowNum = 0;
		row = sheet.getRow(0);
		//find Column number
		for(int i=0;i<=row.getLastCellNum();i++){
			if(row.getCell(i).getStringCellValue().equalsIgnoreCase(colName)){
				colNum = i;
				break;
			}
		}
		//find Row number
		for(int i=1;i<=sheet.getLastRowNum();i++){
			row = sheet.getRow(i);
			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(testCase)){
				rowNum = i;
				break;
			}
		}
		//Goto appropriate row
		row = sheet.getRow(rowNum);
		// Set data to row and column
		row.getCell(colNum).setCellValue(colData);
		try {
			fileOut = new FileOutputStream(TESTDATA_SHEET_PATH);
			book.write(fileOut);
		    fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		File dest = new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png");
		FileUtils.copyFile(scrFile, dest);
		logger.log(LogStatus.INFO, "Snapshot below: " + logger.addScreenCapture(dest.toString()));
	}
	
	public static void logScreenshotToReport(){
		try {
			getScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public static void getScreenshot() throws IOException{
		TakesScreenshot screenShot = (TakesScreenshot) driver;
		File src = screenShot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "\\screenshots\\"+"Report_"+getDateAndTime()+".jpg");
		FileUtils.copyFile(src, dest);
		logger.log(LogStatus.INFO, "Snapshot below: " + logger.addScreenCapture(dest.toString()));
	}
	public static String getDateAndTime(){
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateFormat = simple.format(date);
		return dateFormat;		
	}
}
