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
import javax.swing.JOptionPane;
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
	
	public static void campos(JComboBox box, String path, boolean tabla){
		
		box.removeAllItems();
		
		String fichero=CInicialExcel.getFichero();
	
		try {
			
			FileInputStream fis = new FileInputStream(new File(fichero));
			Workbook workbook=null;
			
			if(fichero.endsWith("xls"))  workbook= new HSSFWorkbook(fis);
			else workbook= new XSSFWorkbook(fis);
			
			if(tabla){
				
				int tablas= workbook.getNumberOfSheets();
				for(int i=0; i<tablas;i++) box.addItem(workbook.getSheetName(i));
				
			}else{
				Sheet hoja= workbook.getSheet(path);
				col= hoja.getRow(0).getPhysicalNumberOfCells();
				
				for(int i=0; i<col;i++) box.addItem(hoja.getRow(0).getCell(i).getStringCellValue());
			}
		
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
		
	}

	public static Object[][] creaConsulta(JComboBox[] box, boolean bfecha, String selectedItem, JRadioButton[] tipos){
		
		Object[][] datos;
		String fichero=CInicialExcel.getFichero();
		String[] data= new String[box.length];
		for(int i=0; i<data.length;i++) data[i]= (String)box[i].getSelectedItem();
		
		try {
			
			FileInputStream fis = new FileInputStream(new File(fichero));
			Workbook workbook=null;
			
			if(fichero.endsWith("xls"))  workbook= new HSSFWorkbook(fis);
			else workbook= new XSSFWorkbook(fis);
			
			Sheet hoja= workbook.getSheet(data[0]);
			Sheet hoja2= workbook.getSheet(data[2]);
			
			int col1= 0, col2=0, colJoin=0, colJoin2=0;
			
			for(int i=0;i<hoja.getRow(0).getPhysicalNumberOfCells();i++){
				String colA= hoja.getRow(0).getCell(i).getStringCellValue();
				if(colA.equals(data[1]))  col1=i; 	 
				
				for(int j=0; j< hoja2.getRow(0).getPhysicalNumberOfCells();j++){
					String colB= hoja2.getRow(0).getCell(j).getStringCellValue();
					
					if(colB.equals(data[3])) col2=j;
					if(colA.equals(colB)){ colJoin=i; colJoin2=j; }
				}
			}
			
			int rows= hoja.getPhysicalNumberOfRows(), rows2= hoja2.getPhysicalNumberOfRows();
			if(rows==0)rows=2;
			if(rows2==0) rows2=2; 
			
			Map<String,Object> map= new LinkedHashMap<String,Object>();
			Cell cJoin= null;
			Cell cJoin2=null;
			
				//AQUI ALMACENAMOS LA PRIMERA COLUMNA DE LA TABLA
			for(int i=1; i<rows; i++){
				Cell c= hoja.getRow(i).getCell(col1);
				cJoin=  hoja.getRow(i).getCell(colJoin);
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
				boolean error= false;
				for(int j=1; j<rows2; j++){
					
					Cell c2= hoja2.getRow(j).getCell(col2);
					cJoin2=  hoja2.getRow(j).getCell(colJoin2);
					//FALTA COMPROBAR TIPO DE DATO
					if(checkJoin(cJoin, cJoin2)){
						
						if(tipos[1].isSelected()){
							try{
								if(map.containsKey(key)) map.put(key, (int)map.get(key)+(int)c2.getNumericCellValue());
								else map.put(key, c2.getNumericCellValue());
							
							}catch(IllegalStateException e){ error= true; JOptionPane.showMessageDialog(null, "Porfavor escoja un campo numérico si quiere hacer valor/sumatorio"); break;}
						
						}else if(tipos[2].isSelected()){
							if(map.containsKey(key)) map.put(key, (int)map.get(key)+1);
							else map.put(key, 1);
						
						}else{
							try{
								map.put(key, c2.getNumericCellValue());
							}catch(IllegalStateException e){ error= true; JOptionPane.showMessageDialog(null, "Porfavor escoja un campo numérico si quiere hacer valor/sumatorio"); break;}		
							
						}
					}// c == c2
				}//for j
				if(error) break;
			}//for i
			
			//if(checkJoin(cJoin, cJoin2)){
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
			/*}else{
				JOptionPane.showMessageDialog(null, "Las hojas de excel no tienen un campo que coincida", "Error de Integridad", 0);
				datos= new Object[1][2];
			}*/
			
		
			
		} catch (FileNotFoundException e) { e.printStackTrace(); datos= new Object[1][2];
		} catch (IOException e) { e.printStackTrace(); 			 datos= new Object[1][2];}
		
		return datos;
	}
	
	public static void rellenaTabla(JTable tabla, Object[][] datos, String x, String y) {
		
		String[] tit={x,y};
		tabla.setModel(new DefaultTableModel(datos,tit));
		
	}
	
	public static boolean checkJoin(Cell c, Cell c2){
	
		//System.out.println(c.getNumericCellValue());
		int tipo=0;
		switch(c.getCellType()){
		
			case Cell.CELL_TYPE_STRING: tipo=1; break;
			case Cell.CELL_TYPE_NUMERIC:
				if(DateUtil.isCellDateFormatted(c)) tipo=2;
				else tipo=3;
				break;
		}
		
		if(tipo==1){  if(c.getStringCellValue().equals(c2.getStringCellValue()))  	 	return true; }
		else if(tipo==2){  if(c.getDateCellValue()==c2.getDateCellValue())			 	return true; }
		else if(tipo==3){  if(c.getNumericCellValue()==c2.getNumericCellValue())	 	return true; }
		 
		return false;		
	}
}
