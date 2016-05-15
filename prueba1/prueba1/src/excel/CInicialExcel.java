package excel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import prueba1.Metodos;




public class CInicialExcel {

	
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
	
	public static void rellenaTablas(final String[] path,final JTable tabla, final JButton[] botones){
		
		for(int i=0; i<botones.length; i++){
			botones[i].addActionListener(new ActionListener(){
		
				@Override
				public void actionPerformed(ActionEvent e) {
					
					for(int n=0; n<botones.length;n++){
						if(botones[n]==e.getSource())botones[n].setForeground(Color.GREEN);
						else botones[n].setForeground(Color.BLACK);
					}
					
					try {
						
						FileInputStream fis= new FileInputStream(new File(path[1]));
						Workbook workbook=null;
						
						if(path[0].endsWith("xls"))  workbook= new HSSFWorkbook(fis);
						else workbook= new XSSFWorkbook(fis);
						
						Sheet hoja= workbook.getSheet(e.getActionCommand());
						
						int filas= hoja.getPhysicalNumberOfRows();
						int col=0;
						
						for(int j=0;j<filas;j++){
							int max= hoja.getRow(j).getPhysicalNumberOfCells();
							if(max>col) col=max;
						}
						
						if(filas==0)filas=2;
						
						Object[][] data= new Object[filas-1][col];
						String[] titulos= new String[col];
						
						for(int j=0; j<filas; j++){
							for(int z=0; z<col;z++){
								
								Cell celda= hoja.getRow(j).getCell(z);
								
								if(j==0) titulos[z]= celda.getStringCellValue();
								else{
									
									switch(celda.getCellType()){
									
									case Cell.CELL_TYPE_STRING: data[j-1][z]=celda.getStringCellValue(); break;
									case Cell.CELL_TYPE_NUMERIC: 
										if(DateUtil.isCellDateFormatted(celda))data[j-1][z]= celda.getDateCellValue();
										else data[j-1][z]=celda.getNumericCellValue();
										break;
									default: data[j-1][z]=null; break;
									}
									
								}
							}
						}
						
						fis.close();
						tabla.setModel(new DefaultTableModel(data,titulos));
					
					} catch (FileNotFoundException err) { err.printStackTrace();
					} catch (IOException err) { err.printStackTrace();  } 
				}
			});
		}
	}
}
