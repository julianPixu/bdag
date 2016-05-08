package prueba1;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Ventana2 {

	static String path;
	
	public static void main(String[] args) {
		
			final JFrame frame= Metodos.creaVentana("DB2C", 600,220);
			UIManager.put("InternalFrame.activeTitleBackground", new ColorUIResource(Color.BLACK));
			
			final JButton btnExaminar, btnContinuar, btnCancelar;
			final JLabel label1, label_fichero, icono, label_error;
			
			label1 = new JLabel("Busque la base de datos sobre la que trabajar:");
			label1.setBounds(60,10,400,30);
			
			icono=new JLabel();
			icono.setBounds(10, 35, 38, 33);
			
			
			label_fichero = new JLabel();
			label_fichero.setBounds(60, 40, 360, 20);
			label_fichero.setBackground(Color.WHITE);
			label_fichero.setOpaque(true);
			
			label_error = new JLabel("* El fichero escogido no es una BBDD MySQL, MongoDB o Excel");
			label_error.setBounds(60,60,400,30);
			label_error.setForeground(Color.RED);
			label_error.setVisible(false);
			
			btnContinuar = Metodos.creaBoton("conti.jpg");
			btnCancelar = Metodos.creaBoton("cancel.jpg");
			
			btnExaminar = Metodos.creaBoton("look.jpg");
			btnExaminar.setBounds(450, 30, 122, 45);
			btnExaminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					try{
						 UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				     
					 }catch (ClassNotFoundException e1){	e1.printStackTrace();
				     }catch (InstantiationException e1){	e1.printStackTrace();
				     }catch (IllegalAccessException e1){	e1.printStackTrace();
				     }catch (UnsupportedLookAndFeelException e1){	e1.printStackTrace();	}
					
						
					JFileChooser chooser= new JFileChooser();
						chooser.setBounds(0, 0, 500, 400);
						chooser.addChoosableFileFilter(new FileNameExtensionFilter("MySQL database", "sql"));
						chooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003", "xls"));
						chooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel 2007 & later", "xlsx"));
						//chooser.addChoosableFileFilter(new FileNameExtensionFilter("MongoDB database", "sql"));
					
					if(chooser.showOpenDialog(chooser)==JFileChooser.APPROVE_OPTION){
						File fichero= chooser.getSelectedFile();
						String nombre= fichero.getName();
						label_fichero.setText(nombre);
						path=fichero.getPath();
						
						if(nombre.endsWith("sql")||nombre.endsWith("db")){
							btnContinuar.setEnabled(true);
							label_error.setVisible(false);
							Image img=new ImageIcon("imagenes/ook.jpg").getImage().getScaledInstance(38, 33, Image.SCALE_SMOOTH);
							icono.setIcon(new ImageIcon(img));
							
						}else{
							label_error.setVisible(true);
							Image img=new ImageIcon("imagenes/no_ok.jpg").getImage().getScaledInstance(38, 33, Image.SCALE_SMOOTH);
							icono.setIcon(new ImageIcon(img));
							btnContinuar.setEnabled(false);
						}
						
						
						
					}
				}
			});
			
			
			btnContinuar.setBounds(300, 125, 122, 45);
			btnContinuar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String[] args= new String[2];
					args[0]= label_fichero.getText();
					args[1]=path;
					if(args[0].endsWith("sql")||args[0].endsWith("db")){
						frame.dispose();
						MySqlMethods.ventanaConexion(args);
					}
						
					
				}
			});
			btnContinuar.setEnabled(false);
			
			
			btnCancelar.setBounds(450, 125, 122, 45);
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
			
			frame.add(label1);
			frame.add(icono);
			frame.add(label_fichero);
			frame.add(label_error);
			frame.add(btnExaminar);
			frame.add(btnContinuar);
			frame.add(btnCancelar);
			frame.setVisible(true);
	}
}
