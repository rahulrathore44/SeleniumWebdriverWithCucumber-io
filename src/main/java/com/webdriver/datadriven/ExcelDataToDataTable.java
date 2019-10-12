package com.webdriver.datadriven;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.webdriver.excelreader.ExcelReader;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;

/**
* rathr1
* 
**/
public class ExcelDataToDataTable implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry typeRegistry) {
		TableEntryTransformer<Excel> entryTransformer = new TableEntryTransformer<Excel>() {
			
			//entry[[Path,C:\\Data\\log\\TestData.xlsx],[SheetIndex,0]]
			
			@Override
			public Excel transform(Map<String, String> entry) throws Throwable {
				ExcelReader reader = new ExcelReader.ExcelReaderBuilder()
						.setFileLocation(entry.get("Path"))
						.setSheet(entry.get("SheetIndex"))
						.build();
				
				List<List<String>> excelData = getExcelData(reader);
				return new Excel(excelData);
			}
		};
		
		typeRegistry.defineDataTableType(new DataTableType(Excel.class, entryTransformer));
		
	}
	
	private List<List<String>> getExcelData(ExcelReader reader) {
		List<List<String>> excelData = new LinkedList<>();
		
		try {
			excelData = reader.getSheetDataAt();
		} catch (InvalidFormatException | IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return excelData;
	}

}
