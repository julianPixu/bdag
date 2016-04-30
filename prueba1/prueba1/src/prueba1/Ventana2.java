package prueba1;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;
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

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Ventana2 {

	private JFrame frame;
	private JButton btnExaminar;
	private JLabel label_fichero;
	private JLabel label_error;
	private JLabel label1;
	private JButton btnContinuar;
	private JButton btnCancelar;
	private JLabel icono;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana2 window = new Ventana2();
					window.frame.setVisible(true);
					
					 
				
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ventana2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(100, 192, 45));
		frame.setBounds(100, 100, 600, 220);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/logoicon.jpg").getScaledInstance(570, 570, Image.SCALE_SMOOTH));
		frame.setTitle("DB2C");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		
		btnExaminar = new JButton("EXAMINAR");
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
					chooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel 2007", "xlsx"));
					//chooser.addChoosableFileFilter(new FileNameExtensionFilter("MongoDB database", "sql"));
				
				if(chooser.showOpenDialog(chooser)==JFileChooser.APPROVE_OPTION){
					File fichero= chooser.getSelectedFile();
					String nombre= fichero.getName();
					label_fichero.setText(fichero.getName());
					
					if(nombre.endsWith("txt")){
						btnContinuar.setEnabled(true);
						label_error.setVisible(false);
						Image img=new ImageIcon("imagenes/ok.jpg").getImage().getScaledInstance(38, 33, Image.SCALE_SMOOTH);
						icono.setIcon(new ImageIcon(img));
						
					}else{
						label_error.setVisible(true);
						Image img=new ImageIcon("imagenes/no_ok.jpg").getImage().getScaledInstance(38, 33, Image.SCALE_SMOOTH);
						icono.setIcon(new ImageIcon(img));
					}
					
					
					
				}
			}
		});
		
		label_fichero = new JLabel();
		label_fichero.setBackground(Color.WHITE);
		label_fichero.setOpaque(true);
		
		icono=new JLabel();
		
		label1 = new JLabel("Busque la base de datos sobre la que trabajar:");
		
		label_error = new JLabel("El fichero escogido no es una BBDD MySQL, MongoDB o Excel");
		label_error.setForeground(Color.RED);
		label_error.setVisible(false);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		btnContinuar = new JButton("CONTINUAR");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Aqui entramos en el programa pofin!");
				String[] args= new String[1];
				args[0]= label_fichero.getText();
				VentanaPrincipal.main(args);
			}
		});
		btnContinuar.setEnabled(false);
		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label1, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
							.addGap(168))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(icono, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnContinuar, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(label_fichero, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
									.addComponent(btnExaminar, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
								.addComponent(label_error, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnExaminar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(icono, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_fichero, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_error, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnContinuar, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(16))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
