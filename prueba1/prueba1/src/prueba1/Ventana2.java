package prueba1;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.IconUIResource;

import Design.Metodos;
import sql.CargaInicial;


import java.awt.Button;
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
			
			final JButton btnExaminar, btnContinuar, btnCancelar,btnAyuda;
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
			
			btnAyuda = Metodos.creaBoton("help.jpg");
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
						
						if(nombre.endsWith("sql")||nombre.endsWith("xlsx")|| nombre.endsWith("xls")){
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
			
			
			btnAyuda.setBounds(13, 125, 122, 45);
			btnAyuda.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 UIManager ui = new UIManager();
					 
					
					try{
						 UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				     
					 }catch (ClassNotFoundException e1){	e1.printStackTrace();
				     }catch (InstantiationException e1){	e1.printStackTrace();
				     }catch (IllegalAccessException e1){	e1.printStackTrace();
				     }catch (UnsupportedLookAndFeelException e1){	e1.printStackTrace();	}
					
					String s="Seleccione un archivo excel o sql de su equipo para realizar las consultas";
					Image img;
					JPanel contenedor= new JPanel();
					 
					try {
						ImageIcon i= new ImageIcon(ImageIO.read(new File("imagenes/botons/exit.jpg")));
						
						
						img= i.getImage().getScaledInstance(122, 45, Image.SCALE_SMOOTH);
						
						 contenedor.add(new JLabel(new ImageIcon(img)));
						 
						 ui.put("OptionPane.messageForeground",Color.white);
						
						ui.put("OptionPane.background", new ColorUIResource(255,215,0));
						
						 ui.put("Panel.background", new ColorUIResource(255,215,0));
						 UIManager.put("Button.background",new ColorUIResource(255,215,0));
						
						// ui.put("OptionPane.buttonFont",new FontUIResource(new Font("System", Font.BOLD, 20)));
						// ui.put("OptionPane.messageFont",new FontUIResource(new Font("System", Font.BOLD, 20)));
						
					//	ui.put("OptionPane.okIcon",new IconUIResource(new ImageIcon(img) ));
						 ui.put("OptionPane.okIcon",new IconUIResource(Metodos.creaBicon("imagenes/botons/exit.jpg") ));
						 
						
						 
						// ui.put("OptionPane.buttonPadding",0);
						// ui.put("OptionPane.setButtonMargin",true);
						 
						 //
						ui.put("OptionPane.okButtonText","");
						
						ui.put("Button.contentAreaFilled",true);
						ui.put("Button.opaque",true);
						
					//	ui.put("OptionPane.buttonAreaBorder",new BorderUIResource(BorderFactory.createEmptyBorder()));
						//ui.put("OptionPane.buttonAreaBorder.Color",new ColorUIResource(new Color(255,215,0)) );
						
						
						 
						// ui.put("Button.background", Color.yellow);
						// ui.put("OptionPane.messagebackground", Color.blue);
						// ui.put("OptionPane.textbackground", Color.green);
						// ui.put("OptionPane.messageAreaBorder",Color.yellow);
						//ui.put("OptionPane.informationIcon", null);
						 
						 /*OptionPane.messageAreaBorder
						   OptionPane.background
						   OptionPane.font
						   OptionPane.minimumSize
						   OptionPane.errorDialog.border.background
						   OptionPane.errorDialog.titlePane.shadow
						   OptionPane.warningDialog.titlePane.shadow
						   OptionPane.buttonClickThreshhold
						   OptionPane.informationIcon
						   OptionPane.warningDialog.border.background
						   OptionPane.border
						   OptionPane.messageForeground
						   OptionPane.background
						   OptionPane.errorDialog.titlePane.background
						   OptionPane.buttonAreaBorder
						   OptionPane.informationSound
						   OptionPane.messageFont
						   *
						   */
						 
						
						 
						 
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					 JOptionPane jop= new JOptionPane();
					 jop.setOpaque(false);
					 
					 
					
				
					
					// ui.put("OptionPane.buttonAreaBorder",);
					 jop.showMessageDialog(null,"vvvvklzkj nkxj b","Ayuda",JOptionPane.INFORMATION_MESSAGE,null);
					 
					
					
					
					//JOptionPane jop= new JOptionPane();
					//JFrame frameAyuda= Metodos.creaVentana("", 600,220);
					
					
					
				/*	try {
						//jop.setIcon(new ImageIcon(ImageIO.read(new File("imagenes/botons/fondo.jpg"))));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				//	jop.setBounds(10, 10, 600, 200);
				//	jop.showMessageDialog(null, s
				//		, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
*/
					
				}
			});
		
			
			
			btnContinuar.setBounds(300, 125, 122, 45);
			btnContinuar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String[] args= new String[2];
					args[0]= label_fichero.getText();
					args[1]=path;
					
					if(args[0].endsWith("sql")){
						frame.dispose();
						CargaInicial.ventanaConexion(args);
						
					}else if(args[0].endsWith("xlsx")||args[0].endsWith("xls")){
						frame.dispose();
						VentanaPrincipal.main(args);
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
			frame.add(btnAyuda);
			frame.add(btnContinuar);
			frame.add(btnCancelar);
			frame.setVisible(true);
	}
}

