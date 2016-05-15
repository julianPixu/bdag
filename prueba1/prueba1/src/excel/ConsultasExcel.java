package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hslf.model.Table;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConsultasExcel {

	public static int col;
	
	public static JComboBox campos(String tabla){
		
		JComboBox box= new JComboBox();;
		
		String fichero=CInicialExcel.getFichero();
	
		try {
			
			FileInputStream fis = new FileInputStream(new File(fichero));
			Workbook workbook=null;
			
			if(fichero.endsWith("xls"))  workbook= new HSSFWorkbook(fis);
			else workbook= new XSSFWorkbook(fis);
			
			Sheet hoja= workbook.getSheet(tabla);
			
			col= hoja.getRow(0).getPhysicalNumberOfCells();
			
			for(int i=0; i<col;i++) box.addItem(hoja.getRow(0).getCell(i).getStringCellValue());
		
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
		return box;
	}

	public static void rellenaTabla(JTable tabla, String selectedItem, String selectedItem2, String text) {
		
		String fichero=CInicialExcel.getFichero();
		
		try {
			
			FileInputStream fis = new FileInputStream(new File(fichero));
			Workbook workbook=null;
			
			if(fichero.endsWith("xls"))  workbook= new HSSFWorkbook(fis);
			else workbook= new XSSFWorkbook(fis);
			
			Sheet hoja= workbook.getSheet(text);
			
			int col1=0, col2=0;
			
			for(int i=0;i<col;i++){
				if(selectedItem.equals(hoja.getRow(0).getCell(i).getStringCellValue())) col1=i;
				if(selectedItem2.equals(hoja.getRow(0).getCell(i).getStringCellValue())) col2=i;
			}
			
			int rows= hoja.getPhysicalNumberOfRows();
			if(rows==0)rows=2;
			
			String[] titulo={selectedItem,selectedItem2};
			Object[][] data=new Object[rows-1][2];
			
			for(int i=1; i<rows; i++){
				Cell c= hoja.getRow(i).getCell(col1);
				Cell c2= hoja.getRow(i).getCell(col2);
				
				
				switch(c.getCellType()){
				
					case Cell.CELL_TYPE_STRING: data[i-1][0]=c.getStringCellValue(); break;
					case Cell.CELL_TYPE_NUMERIC: 
						if(DateUtil.isCellDateFormatted(c))data[i-1][0]= c.getDateCellValue();
						else data[i-1][0]=c.getNumericCellValue();
						break;
					default: data[i-1][0]=null; break;
				}
				switch(c2.getCellType()){
				
					case Cell.CELL_TYPE_STRING: data[i-1][1]=c2.getStringCellValue(); break;
					case Cell.CELL_TYPE_NUMERIC: 
						if(DateUtil.isCellDateFormatted(c2))data[i-1][1]= c2.getDateCellValue();
						else data[i-1][1]=c2.getNumericCellValue();
						break;
					default: data[i-1][1]=null; break;
				}
			}
			
			tabla.setModel(new DefaultTableModel(data,titulo));
			
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
	}
}
