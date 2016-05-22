package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Consultas {
	
	
	public static JComboBox campos(String tabla){
		
		JComboBox box= new JComboBox();
		
		CargaInicial.connect(CargaInicial.getUrl(), CargaInicial.getUser(), CargaInicial.getPswd());
		Connection c= CargaInicial.getConexion();
		
		try {
			Statement st= c.createStatement();
			ResultSet rs= st.executeQuery("SELECT * FROM "+tabla);
			ResultSetMetaData meta= rs.getMetaData();
			
			int col= meta.getColumnCount();
			
			for(int i=1; i<col+1; i++) box.addItem(meta.getColumnName(i));
		
			rs.close();
			st.close();
			CargaInicial.disconnect();
		
		} catch (SQLException e) {e.printStackTrace(); }
		
		return box;
	}

	public static Object[][] creaConsulta(JComboBox[] box, String table, boolean bfecha, String selectedItem, JRadioButton[] tipos) {
		
		Object[][] datos;
		String sql= "SELECT ";
		
		if(bfecha){
			switch(selectedItem){
				case "DIAS":	sql+="FORMAT("+(String)box[0].getSelectedItem()+",\"DD-MM-yyyy\"),"; 	break;
				case "MESES":	sql+="FORMAT("+(String)box[0].getSelectedItem()+",\"MM-yyyy\"),"; 		break;
				case "AÑOS":	sql+="FORMAT("+(String)box[0].getSelectedItem()+",\"yyyy\"),";			break;
			}		
		}else sql+= (String)box[0].getSelectedItem()+",";
		
		if(tipos[1].isSelected()) sql+= "SUM("+(String)box[1].getSelectedItem()+") AS "+(String)box[1].getSelectedItem()+" ";
		else if(tipos[2].isSelected()) sql+= "COUNT("+(String)box[1].getSelectedItem()+") AS "+(String)box[1].getSelectedItem()+" ";
		else sql+= (String)box[1].getSelectedItem()+" ";	 
		
		sql+= "FROM "+table+" GROUP BY "+(String)box[1].getSelectedItem()+" ORDER BY "+(String)box[1].getSelectedItem()+";";
		
		CargaInicial.connect(CargaInicial.getUrl(), CargaInicial.getUser(), CargaInicial.getPswd());
		Connection c= CargaInicial.getConexion();
		try{
			
			Statement st= c.createStatement();
			
			ResultSet rs= st.executeQuery(sql);
			ResultSetMetaData meta= rs.getMetaData();
			
			int col= meta.getColumnCount();
			rs.last();
			int rows= rs.getRow();
			rs.beforeFirst();
			
			datos= new Object[rows][col];
			
			while(rs.next()){
				int r= rs.getRow()-1;
				for(int i=0; i<col;i++) datos[r][i]= rs.getObject(i+1);
												
			}
			rs.close();
			st.close();
			CargaInicial.disconnect();
			
		}catch(SQLException e){e.printStackTrace(); datos= new Object[1][2];}
		
		return datos;
	}
	
	public static void rellenaTabla( JTable tabla,Object[][] datos, String x, String y){
		
		String[] tit={x,y};
		tabla.setModel(new DefaultTableModel(datos,tit));
		
	}

}
