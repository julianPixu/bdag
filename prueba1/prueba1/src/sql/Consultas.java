package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Consultas {
	
	
	public static void campos(JComboBox box, String sql){
		
		box.removeAllItems();
		CargaInicial.connect(CargaInicial.getUrl(), CargaInicial.getUser(), CargaInicial.getPswd());
		Connection c= CargaInicial.getConexion();
		
		try {
			Statement st= c.createStatement();
			ResultSet rs= st.executeQuery(sql);

			while(rs.next())box.addItem(rs.getString(1));
		
			rs.close();
			st.close();
			CargaInicial.disconnect();
		
		} catch (SQLException e) {e.printStackTrace(); }
		
		
	}

	public static Object[][] creaConsulta(JComboBox[] box, boolean bfecha, String selectedItem, JRadioButton[] tipos) {
		
		Object[][] datos= new Object[1][1];
		String sql= "SELECT ";
		String[] data= new String[box.length];
		for(int i=0; i<data.length; i++) data[i]= (String)box[i].getSelectedItem();
		
		if(bfecha){
			switch(selectedItem){
				case "DIAS":	sql+="FORMAT("+data[0]+"."+data[1]+",\"DD-MM-yyyy\") AS Fecha,"; 	break;
				case "MESES":	sql+="FORMAT("+data[0]+"."+data[1]+",\"MM-yyyy\") AS Fecha,"; 		break;
				case "AÑOS":	sql+="FORMAT("+data[0]+"."+data[1]+",\"yyyy\") AS Fecha,";			break;
			}		
		}else sql+= data[0]+"."+data[1]+",";
		
		if(tipos[1].isSelected()) sql+= "SUM("+data[3]+") AS "+data[3]+" ";
		else if(tipos[2].isSelected()) sql+= "COUNT("+data[3]+") AS "+data[3]+" ";
		else sql+= data[3]+" ";	 
		
		if(data[0].equals(data[2])) sql+= "FROM "+data[0]+" GROUP BY "+data[1]+" ORDER BY "+data[1]+";";
		else{
			
			boolean join= false;
			for(int i=0; i< box[1].getItemCount(); i++){
				String campo= (String)box[1].getItemAt(i);
				for(int j=0;j<box[3].getItemCount(); j++){
					
					String campo2=(String)box[3].getItemAt(j);
					if(campo.equals(campo2)){
						join= true;
						sql+= "FROM "+data[0]+","+data[2]+" WHERE "+data[0]+"."+campo+"="+data[2]+"."+campo+" GROUP BY "+campo+" ORDER BY "+campo+";";
					}
				}
			}
			
		}
		
		System.out.println(sql);
		
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
		
		if(!checkNum(datos[0][1], tipos)){
			JOptionPane.showMessageDialog(null, "Porfavor escoja un campo numérico si quiere hacer valor/sumatorio");
			datos= new Object[1][2];
		}
	
		return datos;
		
	}
	
	public static void rellenaTabla( JTable tabla,Object[][] datos, String x, String y){
		
		String[] tit={x,y};
		tabla.setModel(new DefaultTableModel(datos,tit));
		
	}
	
	public static boolean checkNum(Object o, JRadioButton[] tipos){
		
		try{
			if(tipos[0].isSelected()|| tipos[1].isSelected()){
				
				if((int)o>0) return true;
			
			}else return true;
			
		}catch(ClassCastException e){ return false;}
		
		return false;
	}

}
