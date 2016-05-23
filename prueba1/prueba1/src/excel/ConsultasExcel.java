package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
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

	public static Object[][] creaConsulta(JComboBox[] box, String tabla, boolean bfecha, String selectedItem, JRadioButton[] tipos){
		
		Object[][] datos;
		String fichero=CInicialExcel.getFichero();
		
		try {
			
			FileInputStream fis = new FileInputStream(new File(fichero));
			Workbook workbook=null;
			
			if(fichero.endsWith("xls"))  workbook= new HSSFWorkbook(fis);
			else workbook= new XSSFWorkbook(fis);
			
			Sheet hoja= workbook.getSheet(tabla);
			
			int col1=0, col2=0;
			
			for(int i=0;i<col;i++){
				if(((String)box[0].getSelectedItem()).equals(hoja.getRow(0).getCell(i).getStringCellValue())) col1=i;
				if(((String)box[1].getSelectedItem()).equals(hoja.getRow(0).getCell(i).getStringCellValue())) col2=i;
			}
			
			int rows= hoja.getPhysicalNumberOfRows();
			if(rows==0)rows=2;
			
			Map<String,Object> map= new LinkedHashMap<String,Object>();
			
			for(int i=1; i<rows; i++){
				Cell c= hoja.getRow(i).getCell(col1);
				Cell c2= hoja.getRow(i).getCell(col2);
				String key="";
				if(bfecha){
					
					switch(selectedItem){
						case "DIAS":	key=c.getDateCellValue().toString();								break;
						case "MESES":	key=c.getDateCellValue().toString().substring(3);		break;
						case "AÑOS":	key=c.getDateCellValue().toString().substring(5);		break;
					}		
				}else{
					switch(c.getCellType()){
						case Cell.CELL_TYPE_STRING: 	key=c.getStringCellValue();  break;
						case Cell.CELL_TYPE_NUMERIC: 	key= Integer.toString((int)c.getNumericCellValue()); break;
					}
				}
				
				map.put(key, 0);
				if(tipos[1].isSelected()){
					if(map.containsKey(key)) map.put(key, (int)map.get(key)+c2.getNumericCellValue());
					else map.put(key, c2.getNumericCellValue());
				
				
				}else if(tipos[2].isSelected()){
					if(map.containsKey(key)) map.put(key, (int)map.get(key)+1);
					else map.put(key, 1);
				
				}else{
					switch(c2.getCellType()){
						case Cell.CELL_TYPE_STRING: map.put(key, c2.getStringCellValue()); break;
						case Cell.CELL_TYPE_NUMERIC: 
							if(DateUtil.isCellDateFormatted(c2)) map.put(key, c2.getDateCellValue());
							else map.put(key, c2.getNumericCellValue());
							break;
						default: map.put(key,""); break;
					}
				}
			}
			
			
			Set s= map.keySet();
			datos= new Object [s.size()][2];
			Iterator it= s.iterator();
			int cont=0;
			
			while(it.hasNext()){
				String str= (String)it.next();
				datos[cont][0]= str;
				datos[cont][1]= map.get(str);
				cont++;
			}
			
			
		} catch (FileNotFoundException e) { e.printStackTrace(); datos= new Object[1][2];
		} catch (IOException e) { e.printStackTrace(); 			 datos= new Object[1][2];}
		
		return datos;
	}
	
	public static void rellenaTabla(JTable tabla, Object[][] datos, String x, String y) {
		
		String[] tit={x,y};
		tabla.setModel(new DefaultTableModel(datos,tit));
		
	}
}
