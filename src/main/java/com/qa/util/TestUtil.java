package com.qa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;

import com.qa.base.TestBase;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	public static String TESTDATA_FILE_PATH = System.getProperty("user.dir") + "/src/main/java/com/"
			+ "/qa/testdata/TestData.xlsx";
	public static String TESTDATA_SHEET_NAME = "sheet1";
	public static int HEADER_ROW_INDEX = 0;
	public static int DATA_ROW_INDEX = 1;

	static Workbook book;
	static Sheet sheet;
	static JavascriptExecutor js;

	Map<String, Integer> data_index_map = new HashMap<String, Integer>();

	public TestUtil() {
		data_index_map = getDataIndex();
	}

	public Map<String, Integer> getDataIndex() {

		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_FILE_PATH);
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
		sheet = book.getSheet(TESTDATA_SHEET_NAME);

		Row header_row = sheet.getRow(HEADER_ROW_INDEX);

		for (int k = 0; k < header_row.getLastCellNum(); k++) {
			data_index_map.put(header_row.getCell(k).getStringCellValue(), k);
		}
		return data_index_map;
	}

	public String getData(String columnName) {
		Row data_row = sheet.getRow(DATA_ROW_INDEX);
		return data_row.getCell(data_index_map.get(columnName)).getStringCellValue();
	}

}
