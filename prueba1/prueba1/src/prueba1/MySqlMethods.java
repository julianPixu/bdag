package prueba1;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MySqlMethods {
	
	public static void connect(String path){
		
		File f= new File(path);
		JFrame frame= new JFrame();
		frame.setBounds(0,0,500,220);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/logoicon.jpg").getScaledInstance(570, 570, Image.SCALE_SMOOTH));
		frame.setTitle("Conéctese a la BBDD:");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		JLabel fondo= Metodos.creaImagen("fondo.jpg", 500, 220);
		frame.setContentPane(fondo);
		
		JLabel lb1=new JLabel("usuario:"), lb2= new JLabel("contraseña:");
		Font font= new Font("Serif", Font.BOLD, 16);
			lb1.setBounds(50,10,150,40);
			lb1.setFont(font);
			lb2.setBounds(50,60,150,40);
			lb2.setFont(font);
		JTextField field_user= new JTextField(), field_pswd=new JTextField();
			field_user.setBounds(200,20,250,30);
			field_pswd.setBounds(200,70,250,30);
			
		JButton continuar= new JButton(), cancelar=new JButton();
			continuar.setBounds(200, 130, 122, 45);
			cancelar.setBounds(330, 130, 122, 45);
		frame.add(lb1);
		frame.add(lb2);
		frame.add(field_user);
		frame.add(field_pswd);
		frame.add(continuar);
		frame.add(cancelar);
	
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			if(f.exists()){
				String user=field_user.getText(), pswd= field_pswd.getText();
				Connection c= DriverManager.getConnection("jdbc:mysql://localhost/"+f.getName(),user,pswd);
				
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}catch(SQLException e){
			
		}
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		connect("algo.txt");
	}

}
