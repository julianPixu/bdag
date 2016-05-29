package Design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import prueba1.Ventana2;
import prueba1.VentanaConsultas;

/**Esta clase contiene los métodos genéricos para aplicar en la app
 * 
 * @author Julian&Bea
 *
 */
public class Metodos {
	
	public static JFrame creaVentana(String title, int width, int height){
		
		JFrame frame= new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/logoicon.jpg").getScaledInstance(570, 570, Image.SCALE_SMOOTH));
		frame.setTitle(title);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		JLabel background= Metodos.creaImagen("fondo.jpg",width, height);
		frame.setContentPane(background);

		
		return frame;
		
	}
	
	public static JLabel creaImagen(String imagen, int width, int height){
		
		JLabel fondo=new JLabel();
		try {
			
			ImageIcon ii= new ImageIcon(ImageIO.read(new File("imagenes/"+imagen)));
			Image i= ii.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			fondo= new JLabel(new ImageIcon(i));
		
		} catch (IOException e2) {	System.out.println("Error al insertar fondo"); }
		
		return fondo;
	}
	
	public static JButton creaBoton(String imagen){
		JButton b= new JButton();
		
		try {
			
			ImageIcon ii= new ImageIcon(ImageIO.read(new File("imagenes/botons/"+imagen)));
			Image i= ii.getImage().getScaledInstance(122, 45, Image.SCALE_SMOOTH);
			
			b= new JButton(new ImageIcon(i));
			b.setContentAreaFilled(false);
			b.setBorderPainted(false);
			b.setMargin(new Insets(0,0,0,0));
		
		} catch (IOException e2) {	System.out.println("Error al insertar fondo"); }
		
		return b;
	}
	
	
	public static ImageIcon creaBicon(String imagen){
		
		 Image img=null;
		ImageIcon i,r;
		
		try {
			i= new ImageIcon(ImageIO.read(new File("imagenes/botons/acept.jpg")));
			img= i.getImage().getScaledInstance(122, 45, Image.SCALE_SMOOTH);
			
		
		} catch (IOException e2) {	System.out.println("Error al insertar fondo"); }
		
		 r=new ImageIcon(img);
		 
		
		return r;
	}
	
	
	public static Border creaBorder(){
		Border border=BorderFactory.createEmptyBorder();
	
		JButton b= creaBoton("exit.jpg");
		
		b.setContentAreaFilled(false);
		
		b.setBorderPainted(false);
		
		
		
		
		return border;
	}
	

	
	
	
	
	public static void rellenaTabla(JTable table, String[] colName, Object[][] data){
		
		table.setModel(new DefaultTableModel(data, colName));
		int columnas= table.getColumnCount();
		table.setRowHeight(40);	
		for(int i=0; i<columnas;i++) table.getColumnModel().getColumn(i).setMinWidth(200);
		table.setVisible(true);
	}
	
	public static void muestraMenu(final JLabel label, boolean b, final String[] path, final JTable tabla, final JButton[] botones){
		
		final JPopupMenu menu= new JPopupMenu();
		menu.setBackground(Color.BLACK);
		menu.setPopupSize(new Dimension(150, 200));
		menu.setBounds(400, 400, 300, 100);
		
		if(b){
			JTextArea area= new JTextArea("Hola paaxu\n soy la ventanica\nde ayuda.");
			area.setForeground(Color.WHITE);
			area.setBackground(Color.BLACK);
			area.setOpaque(true);
			menu.add(area);
		}else{
			JMenuItem[] items= new JMenuItem[3];
			items[0]= new JMenuItem("Nuevo");			
			items[1]= new JMenuItem("Consultar");		
			items[2]= new JMenuItem("Cancelar");		
			
			for(int i=0; i<3;i++){
				items[i].setForeground(Color.WHITE);
				items[i].setBackground(Color.BLACK);
				items[i].setOpaque(true);
				items[i].setFont(new Font("Serif",Font.PLAIN,14));
				menu.add(items[i]);
				final int I=i;
				items[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						
						switch(I){
						case 0: Ventana2.main(null); 	break;
						case 1: VentanaConsultas.creaVentana(path, tabla, botones);	 		break;
						case 2: JOptionPane.showMessageDialog(null, "SAYONARA BABY"); 		break;
						}
					}	
				});
			}//for	
		}//else
		
		
		label.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				menu.show(label, 0, 30);
			}
		});
	}
	

}
