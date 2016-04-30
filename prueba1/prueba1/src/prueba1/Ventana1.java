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

	private static JFrame frmDbc;
	private JLabel titulo;
	private JLabel acons;
	private JLabel carga;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana1 window = new Ventana1();
					window.frmDbc.setVisible(true);
					
					 new Timer().schedule(new TimerTask(){
						@Override
						public void run(){
							Ventana2.main(null);
							frmDbc.dispose();
						}
					}, 4300);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ventana1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDbc = new JFrame();
		frmDbc.setResizable(false);
		frmDbc.setTitle("DB2C");
		
		frmDbc.setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/logoicon.jpg").getScaledInstance(570, 570, Image.SCALE_SMOOTH));
		frmDbc.setBounds(20, 20, 600, 400);
		frmDbc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDbc.setLocationRelativeTo(null);
		
		
		
		Image i= new ImageIcon("imagenes/fondo.jpg").getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH);
		
		JLabel label = new JLabel(new ImageIcon(i));
		
		label.setBounds(0, 0, 600, 400);
		label.setLayout(new BorderLayout());
		
		//frmDbc.getContentPane().add(label);
		
		label.setLayout(new BoxLayout(label, BoxLayout.Y_AXIS));
		
		titulo= new JLabel("DB2C");
		titulo.setForeground(SystemColor.text);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Monotype Corsiva", Font.BOLD, 99));
		titulo.setBounds(50, 50, 500, 200);
		titulo.setBorder(BorderFactory.createEmptyBorder(80,170,0,50));
		
		label.add(titulo);
		
		
	
				Image icarga= new ImageIcon("imagenes/loading_bar.gif").getImage();
		carga=new JLabel(new ImageIcon(icarga));
		carga.setBorder(BorderFactory.createEmptyBorder(35,75,50,50));
		label.add(carga);
		
		label.setLayout(new BoxLayout(label, BoxLayout.Y_AXIS));
		
		Image iacons= new ImageIcon("imagenes/logocompany.jpg").getImage().getScaledInstance(70, 50, Image.SCALE_SMOOTH);
		acons = new JLabel(new ImageIcon(iacons));
		acons.setVerticalAlignment(SwingConstants.BOTTOM);
		acons.setBounds(0, 0, 60, 40);
		acons.setBorder(BorderFactory.createEmptyBorder(15,530,50,50));
		label.add(acons);
		
		
				
		
		GroupLayout groupLayout = new GroupLayout(frmDbc.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 594, Short.MAX_VALUE)
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(label, GroupLayout.PREFERRED_SIZE, 371, Short.MAX_VALUE)
		);
		frmDbc.getContentPane().setLayout(groupLayout);
	}
	
	
}
