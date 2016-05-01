package prueba1;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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

}
