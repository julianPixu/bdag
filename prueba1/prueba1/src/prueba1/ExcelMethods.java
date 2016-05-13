package prueba1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class ExcelMethods {

	
	public static JButton[] connect(String [] path, JPanel panel, JButton[] jb, JLabel[] flechas){
		
		try {
			FileInputStream fis= new FileInputStream(new File(path[1]));
			
			Workbook workbook=null;
			
			
			if(path[0].endsWith("xls"))  workbook= new HSSFWorkbook(fis);
			else workbook= new XSSFWorkbook(fis);
				
			int tablas= workbook.getNumberOfSheets();
				
			jb= new JButton[tablas];
			flechas= new JLabel[tablas];
			
			for(int i=0; i<tablas; i++){
				flechas[i]= Metodos.creaImagen("arrow.jpg", 30, 30);
				flechas[i].setBounds(30, (i+1)*40, 30,30);
				jb[i]= new JButton(workbook.getSheetName(i));
				jb[i].setBounds(65,(i+1)*40,120,30);
				jb[i].setContentAreaFilled(false);
				jb[i].setBorderPainted(false);
							
				panel.add(flechas[i]);
				panel.add(jb[i]);
			}
			
			fis.close();
			return jb;
			
		} catch (FileNotFoundException e) { e.printStackTrace(); return null;
		} catch (IOException e) { e.printStackTrace(); return null; } 
		
		
	}
}
