package prueba1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class Ventana1 {

	public static void main(String[] args) {
		
		final JFrame frmDbc= Metodos.creaVentana("DB2C",600, 400);
		frmDbc.setUndecorated(true);
		
		JLabel titulo= new JLabel("DB2C");
			titulo.setForeground(SystemColor.text);
			titulo.setHorizontalAlignment(SwingConstants.CENTER);
			titulo.setFont(new Font("Monotype Corsiva", Font.BOLD, 99));
			titulo.setBounds(50, 50, 500, 200);
			
		ImageIcon i= new ImageIcon("imagenes/loading_bar.gif");
		JLabel carga= new JLabel();
			carga.setBounds(80, 230, 500, 40);
			carga.setIcon(i);
		
		JLabel acons= Metodos.creaImagen("logocompany.jpg", 70, 50);	
		acons.setBounds(500, 330, 70, 50);
		
		frmDbc.add(titulo);
		frmDbc.add(carga);
		frmDbc.add(acons);
		frmDbc.setVisible(true);
		
		new Timer().schedule(new TimerTask(){
			@Override
			public void run(){
				Ventana2.main(null);
				frmDbc.dispose();
			}
		}, 4200);
	
	}
	
}
