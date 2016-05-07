package prueba1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MySqlMethods {
	
	private static Connection conexion;
	
	
	public static void connect(String path){
		
		final File f= new File(path);
		final JFrame frame= Metodos.creaVentana("Conéctese a la BBDD:", 500, 220);
		
		JLabel lb1=new JLabel("usuario:"), lb2= new JLabel("contraseña:");
		Font font= new Font("Serif", Font.BOLD, 16);
			lb1.setBounds(50,10,150,40);
			lb1.setFont(font);
			lb2.setBounds(50,60,150,40);
			lb2.setFont(font);
		final JTextField field_user= new JTextField();
		final JTextField field_pswd=new JTextField();
			field_user.setBounds(200,20,250,30);
			field_pswd.setBounds(200,70,250,30);
			
		JButton continuar= Metodos.creaBoton("conti.jpg"), cancelar= Metodos.creaBoton("cancel.jpg");
			continuar.setBounds(180, 130, 122, 45);
			cancelar.setBounds(330, 130, 122, 45);
		frame.add(lb1);
		frame.add(lb2);
		frame.add(field_user);
		frame.add(field_pswd);
		frame.add(continuar);
		frame.add(cancelar);
	
		frame.setVisible(true);
		
		continuar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String user=field_user.getText(), pswd= field_pswd.getText();
					conexion= DriverManager.getConnection("jdbc:mysql://localhost/"+f.getName(),user,pswd);
							
				}catch (ClassNotFoundException e) { e.printStackTrace();
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null, "usuario o contraseña incorrectos.");	
					//e.printStackTrace();
				}	
			}
		});
		
		cancelar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { frame.dispose(); }
		});
		
		
	}
	
	public static void rellenaPanelTablas(JPanel panel, JButton[] tablas, JLabel[] flechas){
		
		try {
			
			Statement s= conexion.createStatement();
			ResultSet res= s.executeQuery("");
			
			res.last();
			tablas= new JButton[res.getRow()];
			flechas= new JLabel[res.getRow()];
			res.beforeFirst();
			
			while(res.next()){
				int i=res.getRow();
				
				flechas[i]= Metodos.creaImagen("arrow.jpg", 30, 30);
				flechas[i].setBounds(30, i*40, 30,30);
				tablas[i]= new JButton(res.getString(3));
				tablas[i].setBounds(65,i*40,120,30);
				tablas[i].setContentAreaFilled(false);
				tablas[i].setBorderPainted(false);
							
				panel.add(flechas[i]);
				panel.add(tablas[i]);
				
			}
			
			res.close();
			s.close();
		
		
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void rellenaTablas(final JTable tabla, final JButton[] botones){
		
		for(int i=0; i<botones.length; i++){
			botones[i].addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					
					for(int n=0; n<botones.length;n++){
						if(botones[n]==e.getSource())botones[n].setForeground(Color.GREEN);
						else botones[n].setForeground(Color.BLACK);
					}
					
					try {
						Statement s2= conexion.createStatement();
						ResultSet rs= s2.executeQuery("SELECT * FROM "+e.getSource().toString());
						ResultSetMetaData rsmt= rs.getMetaData();
						
						rs.last();
						int row= rs.getRow();
						int col= rsmt.getColumnCount();
						rs.beforeFirst();
						
						String[] columnas= new String[col];
						for(int c=0; c<col; c++) columnas[c]= rsmt.getColumnName(c);
						
						Object[][] datos= new Object[row][col];
						
						while(rs.next()){
							int r= rs.getRow();
							for(int c=0; c<col;c++) datos[r][c]= rs.getObject(c);
						}
						
						tabla.setModel(new DefaultTableModel(datos,columnas));
						
						rs.close();
						s2.close();
						
					}catch (SQLException e1) { e1.printStackTrace(); }
				}		
			});//ActionListener
		}//for
	}
	
	
	
	public static void main(String[] args) {
		connect("algo.txt");
	}

}
