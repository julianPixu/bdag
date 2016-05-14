package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prueba1.MySqlMethods;

public class Consultas {
	
	
	public static JComboBox campos(String tabla){
		
		JComboBox box= new JComboBox();
		
		MySqlMethods.connect(MySqlMethods.getUrl(), MySqlMethods.getUser(), MySqlMethods.getPswd());
		Connection c= MySqlMethods.getConexion();
		
		try {
			Statement st= c.createStatement();
			ResultSet rs= st.executeQuery("SELECT * FROM "+tabla);
			ResultSetMetaData meta= rs.getMetaData();
			
			int col= meta.getColumnCount();
			
			for(int i=1; i<col+1; i++) box.addItem(meta.getColumnName(i));
		
			rs.close();
			st.close();
			MySqlMethods.disconnect();
		
		} catch (SQLException e) {e.printStackTrace(); }
		
		return box;
	}
	
	public static void rellenaTabla( JTable tabla,String x, String y, String tab){
		
		MySqlMethods.connect(MySqlMethods.getUrl(), MySqlMethods.getUser(), MySqlMethods.getPswd());
		Connection c= MySqlMethods.getConexion();
		
		try {
			Statement st= c.createStatement();
			ResultSet rs= st.executeQuery("SELECT "+x+",count("+y+") FROM "+tab+" group by "+x);
			ResultSetMetaData meta= rs.getMetaData();
			
			int col= meta.getColumnCount();
			rs.last();
			int rows= rs.getRow();
			rs.beforeFirst();
			
			String[] tit= {x,y};
			Object[][] datos= new Object[rows][col];
			
			
				
			while(rs.next()){
				int r= rs.getRow()-1;
				for(int i=0; i<col;i++) datos[r][i]= rs.getObject(i+1);
												
			}
			
			tabla.setModel(new DefaultTableModel(datos,tit));
		
			rs.close();
			st.close();
			MySqlMethods.disconnect();
		
		} catch (SQLException e) {e.printStackTrace(); }
	}

}
