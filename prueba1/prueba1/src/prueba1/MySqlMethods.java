package prueba1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	private static String url;
	private static String user;
	private static String pswd;
	
	public static void ventanaConexion(final String [] path){
		
		final File f= new File(path[1]);
		final JFrame frame= Metodos.creaVentana("Conéctese a la BBDD: "+path[0], 500, 220);
		
		JLabel lb1=new JLabel("Usuario:"), lb2= new JLabel("Contraseña:");
		Font font= new Font("Serif", Font.BOLD, 18);
			lb1.setBounds(50,10,150,40);
			lb1.setFont(font);
			lb1.setForeground(Color.WHITE);
			lb2.setBounds(50,60,150,40);
			lb2.setFont(font);
			lb2.setForeground(Color.WHITE);
		final JTextField field_user= new JTextField();
		final JTextField field_pswd=new JTextField();
			field_user.setBounds(180,20,270,30);
			field_pswd.setBounds(180,70,270,30);
			
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
		final boolean b;
		continuar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
					
					url= nomBbdd(path);
					user=field_user.getText();
					pswd= field_pswd.getText();
					
					File f = new File("mysql-5.0.22-win32/bin/mysqld.exe");
					System.out.println(f.getAbsolutePath());
					if(f.exists()){ 
							
							//ejecutamos un archivo ppt desde el PowerPoint
						try {Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+f.getAbsolutePath());} 
						catch(Exception err){ System.out.println("Fallo d ejecucion");err.printStackTrace();} 
						
					}else System.out.println("Fichero no encontrado");
					
					if(connect(url,user,pswd)){
						frame.dispose();
						VentanaPrincipal.main(path);
					}
			}		
		});
		
		cancelar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				frame.dispose(); 
				Ventana2.main(null);}
		});
		
		
	}
	


	public static String nomBbdd(String [] path){
		
		String [] s=null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path[1]));
			String bd= br.readLine().replace("CREATE DATABASE ", "").replace(";", "");
			s= bd.split("¿");
			br.close();
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
		return s[1];
	}
	
	public static boolean connect(String url, String user, String pswd){
		
		try {
			
			
			Class.forName("com.mysql.jdbc.Driver");
			conexion= DriverManager.getConnection("jdbc:mysql://localhost/"+url,user,pswd);
			return true;
		} catch (ClassNotFoundException e) { e.printStackTrace(); return false;
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "usuario o contraseña incorrectos.");	
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void disconnect(){
		try {
			conexion.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static JButton[] rellenaPanelTablas(String [] path,JPanel panel, JButton[] tablas, JLabel[] flechas){
				
		try{
			
			Statement s= conexion.createStatement();
			ResultSet res= s.executeQuery("SELECT * FROM information_schema.tables "
					+ "WHERE table_schema='"+nomBbdd(path)+"';");
			
			res.last();
			tablas= new JButton[res.getRow()];
			flechas= new JLabel[res.getRow()];
			res.beforeFirst();
			
			while(res.next()){
				int i=res.getRow()-1;
				
				flechas[i]= Metodos.creaImagen("arrow.jpg", 30, 30);
				flechas[i].setBounds(30, (i+1)*40, 30,30);
				tablas[i]= new JButton(res.getString(3));
				tablas[i].setBounds(65,(i+1)*40,120,30);
				tablas[i].setContentAreaFilled(false);
				tablas[i].setBorderPainted(false);
							
				panel.add(flechas[i]);
				panel.add(tablas[i]);
				
			}
			
			res.close();
			s.close();
		
			disconnect();
		
		} catch (SQLException e) { e.printStackTrace(); }
		return tablas;
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
					
					connect(url,user,pswd);
					try {
						Statement s2= conexion.createStatement();
						
						ResultSet rs= s2.executeQuery("SELECT * FROM "+e.getActionCommand());
						ResultSetMetaData rsmt= rs.getMetaData();
						
						rs.last();
						int row= rs.getRow();
						int col= rsmt.getColumnCount();
						rs.beforeFirst();
						
						String[] columnas= new String[col];
						for(int c=0; c<col; c++) columnas[c]= rsmt.getColumnName(c+1);
							
						
						Object[][] datos= new Object[row][col];
						while(rs.next()){
							int r= rs.getRow()-1;
							for(int c=0; c<col;c++) datos[r][c]= rs.getObject(c+1);
															
						}
						
						tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						tabla.setModel(new DefaultTableModel(datos,columnas));
						int colum= tabla.getColumnCount();
						tabla.setRowHeight(40);	
						for(int i=0; i<colum;i++) tabla.getColumnModel().getColumn(i).setMinWidth(150);
						
						/*Dimension pantalla= Toolkit.getDefaultToolkit().getScreenSize();
						if(tabla.getWidth()<((int)(pantalla.width*0.71))){
							tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
						}*/
						
						rs.close();
						s2.close();
						disconnect();
						
					}catch (SQLException e1) { e1.printStackTrace(); }
				}		
			});//ActionListener
		}//for
	}
	
	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		MySqlMethods.url = url;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		MySqlMethods.user = user;
	}

	public static String getPswd() {
		return pswd;
	}

	public static void setPswd(String pswd) {
		MySqlMethods.pswd = pswd;
	}



	public static Connection getConexion() {
		return conexion;
	}



	public static void setConexion(Connection conexion) {
		MySqlMethods.conexion = conexion;
	}

}
