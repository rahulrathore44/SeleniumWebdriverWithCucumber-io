package com.webdriver.datadriven;
/**
* rathr1
* 
**/

import java.util.List;

public class Excel {
	private List<List<String>> excelData; // outer list for row,inner list is for column
	
	public Excel(List<List<String>> excelData) {
		this.excelData = excelData;
	}
	
	public List<List<String>> getExcelData() {
		return excelData;
	}
	
}
