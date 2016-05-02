package prueba1;

import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**Esta clase contiene los métodos genéricos para aplicar en la app
 * 
 * @author Julian&Bea
 *
 */
public class Metodos {
	
	public static JLabel CreaFondo(String imagen, int width, int height){
		
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
	
	public static void rellenaTabla(JTable table, String[] colName, Object[][] data){
		
		table.setModel(new DefaultTableModel(data, colName));
		int columnas= table.getColumnCount();
		table.setRowHeight(40);	
		for(int i=0; i<columnas;i++) table.getColumnModel().getColumn(i).setMinWidth(200);
		table.setVisible(true);
	}

}
