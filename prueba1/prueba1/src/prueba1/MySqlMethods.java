package prueba1;

import java.awt.Color;
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
	
	
	public static void connect(final String [] path){
		
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
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String user=field_user.getText(), pswd= field_pswd.getText();
					BufferedReader br= new BufferedReader(new FileReader(path[1]));
					String bd= br.readLine().replace("CREATE DATABASE ", "").replace(";", "");
					String [] s= bd.split("¿");
					br.close();
					System.out.println(s[1]);
					conexion= DriverManager.getConnection("jdbc:mysql://localhost/"+s[1],user,pswd);
					frame.dispose();
					VentanaPrincipal.main(path);
					
					
				}catch (ClassNotFoundException e) { e.printStackTrace();
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null, "usuario o contraseña incorrectos.");	
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		cancelar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { frame.dispose(); }
		});
		
		
	}
	
	public static JButton[] rellenaPanelTablas(String [] path,JPanel panel, JButton[] tablas, JLabel[] flechas){
		
		try {
			
			String [] bd= null;
			try {
				BufferedReader br= new BufferedReader(new FileReader(path[1]));
				String s= br.readLine().replace("CREATE DATABASE ", "").replace(";", "");
				bd= s.split("¿");
				br.close();
			} catch (FileNotFoundException e) {e.printStackTrace();
			} catch (IOException e) {e.printStackTrace();}
			
			
			Statement s= conexion.createStatement();
			ResultSet res= s.executeQuery("SELECT * FROM information_schema.tables WHERE table_schema='"+bd[1]+"';");
			
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
					
					try {
						Statement s2= conexion.createStatement();
						System.out.println(e.getActionCommand());
						ResultSet rs= s2.executeQuery("SELECT * FROM "+e.getActionCommand());
						ResultSetMetaData rsmt= rs.getMetaData();
						
						rs.last();
						int row= rs.getRow();
						int col= rsmt.getColumnCount();
						rs.beforeFirst();
						System.out.println("------------COLUMNAS--------------");
						String[] columnas= new String[col];
						for(int c=0; c<col; c++){
							columnas[c]= rsmt.getColumnName(c+1);
							System.out.println(rsmt.getColumnName(c+1));
						}
						
						Object[][] datos= new Object[row][col];
						System.out.println("------------DATOS--------------");
						while(rs.next()){
							int r= rs.getRow()-1;
							for(int c=0; c<col;c++){ 
								datos[r][c]= rs.getObject(c+1);
								System.out.println(rs.getObject(c+1));
							}
						}
						
						tabla.setModel(new DefaultTableModel(datos,columnas));
						
						rs.close();
						s2.close();
						
					}catch (SQLException e1) { e1.printStackTrace(); }
				}		
			});//ActionListener
		}//for
	}
	
	
	
	/*public static void main(String[] args) {
		connect("algo.txt");
	}*/

}
